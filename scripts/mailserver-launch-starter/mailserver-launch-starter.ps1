Write-Host "___________________________________"
Write-Host "*    MAIL SERVER SETUP SCRIPT     *"
Write-Host "___________________________________"

$scriptDirectory = Split-Path -Parent $MyInvocation.MyCommand.Path
$grandParentDirectory = Split-Path -Parent (Split-Path -Parent $scriptDirectory)
$application_properties_dir = Join-Path (Join-Path $grandParentDirectory "src/main/resources") "application.properties"

# Load application.properties.
if (Test-Path $application_properties_dir) {
    $CONTAINER_NAME = (Select-String -Path $application_properties_dir -Pattern "DOCKER.CONTAINER.NAME.MAILDEV").Line.Split('=')[1].Trim()
    $USERNAME = (Select-String -Path $application_properties_dir -Pattern "spring.mail.username").Line.Split('=')[1].Trim()
    $PASSWORD = (Select-String -Path $application_properties_dir -Pattern "spring.mail.password").Line.Split('=')[1].Trim()
    $MAILDEV_OUTGOING_PORT = (Select-String -Path $application_properties_dir -Pattern "MAILDEV_OUTGOING_PORT").Line.Split('=')[1].Trim()
    $MAILDEV_WEB_PORT = (Select-String -Path $application_properties_dir -Pattern "MAILDEV_WEB_PORT").Line.Split('=')[1].Trim()

    Write-Host "SUCCESS: variables from application.properties are loaded."
} else {
    Write-Host "Error: application.properties file not found."
    exit 1
}

# Checking if docker daemon is running.
$output = (docker info 2>$null)
if ($output -like "*Error: Failed to connect to Docker daemon*") {
    Write-Host "Error: Failed to connect to Docker daemon. Please check if Docker daemon is running and accessible."
    Write-Host "To solve this problem, try using the command depending on your system:"
    Write-Host "    sudo systemctl start docker"
    Write-Host "    sudo service docker start"
    Write-Host "and start script again."
    exit 1
}

# Delete the container if it already exists.
if (docker ps -a --format "{{.Names}}" | Select-String -Pattern "^$CONTAINER_NAME$" -Quiet) {
    Write-Host "Deleting old container: $CONTAINER_NAME"
    docker stop $CONTAINER_NAME >$null 2>&1
    docker rm $CONTAINER_NAME >$null 2>&1
}

$portArgs = "-p", "${MAILDEV_WEB_PORT}:1080", "-p", "${MAILDEV_OUTGOING_PORT}:1025"


$output = docker run -d `
    --name $CONTAINER_NAME `
    $portArgs `
    -e MAILDEV_WEB_USER=$USERNAME `
    -e MAILDEV_WEB_PASS=$PASSWORD `
    maildev/maildev

# Checking error codes and displaying the appropriate message.
$exit_code = $LASTEXITCODE
if ($exit_code -ne 0) {
    if ($output -like "*out of memory*") {
        Write-Host "Error: Out of memory. Insufficient memory available."
    } elseif ($output -like "*name conflict*") {
        Write-Host "Error: Container name conflict. Container with the same name already exists. Most likely the script encountered a problem with deleting an old container with the same name."
    } elseif ($output -like "*No such image*") {
        Write-Host "Error: Image not found. The specified container image does not exist."
    } elseif ($output -like "*Cannot connect to the Docker daemon *") {
        Write-Host "Error: Docker daemon was running at the start of the script but is not responding at the moment."
    } elseif ($output -like "*port is already allocated*") {
        Write-Host "Error: Port conflict. The specified port is already allocated by another container."
    } else {
        Write-Host "Error: Failed to create a new container: $CONTAINER_NAME. Unknown error."
    }
} else {
    Write-Host "Container created successfully: $CONTAINER_NAME"
    Write-Host "___________________________________"
}