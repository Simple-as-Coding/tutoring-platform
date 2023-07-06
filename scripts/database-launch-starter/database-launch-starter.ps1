Write-Host "___________________________________"
Write-Host "*      DATABASE SETUP SCRIPT      *"
Write-Host "___________________________________"

$scriptDirectory = Split-Path -Parent $MyInvocation.MyCommand.Path
$grandParentDirectory = Split-Path -Parent (Split-Path -Parent $scriptDirectory)
$application_properties_dir = Join-Path (Join-Path $grandParentDirectory "src/main/resources") "application.properties"

# Load application.properties
if (Test-Path $application_properties_dir) {
    $CONTAINER_NAME = (Select-String -Path $application_properties_dir -Pattern "spring.datasource.url" | ForEach-Object { $_.Line -split "=" })[1].Split("/")[-1]
    $USERNAME = (Select-String -Path $application_properties_dir -Pattern "spring.datasource.username" | ForEach-Object { $_.Line -split "=" })[1]
    $PASSWORD = (Select-String -Path $application_properties_dir -Pattern "spring.datasource.password" | ForEach-Object { $_.Line -split "=" })[1]
    Write-Host "SUCCESS: variables from application.properties are loaded."
} else {
    Write-Host "Error: application.properties file not found."
    exit 1
}

# Check if the container already exists, if so, delete it
$output = (docker info 2>$null)
if ($output -like "*error during connect*") {
    Write-Host "Error: Failed to connect to Docker daemon. Please check if Docker Desktop is running and accessible."
    Write-Host "___________________________________"
    exit 1
}

if ((docker ps -a --format "{{.Names}}") -contains $CONTAINER_NAME) {
    Write-Host "Deleting old container: $CONTAINER_NAME"
    docker stop $CONTAINER_NAME > $null 2>&1
    docker rm $CONTAINER_NAME > $null 2>&1
}

$output = docker run -d `
    --name $CONTAINER_NAME `
    -e POSTGRES_USER=$USERNAME `
    -e POSTGRES_PASSWORD=$PASSWORD `
    -e POSTGRES_DB=$CONTAINER_NAME `
    -p 5432:5432 `
    postgres 2>&1

# Checking error codes and displaying the appropriate message based on it
$exit_code = $LASTEXITCODE
if ($exit_code -ne 0) {
    if ($output -like "*out of memory*") {
        Write-Host "Error: Out of memory. Insufficient memory available."
    } elseif ($output -like "*conflict*") {
        Write-Host "Error: Container name conflict. Container with the same name already exists. Most likely the script encountered a problem with deleting an old container with the same name."
    } elseif ($output -like "*No such image*") {
        Write-Host "Error: Image not found. The specified container image does not exist."
    } else {
        Write-Host "Error: Failed to create a new container: $CONTAINER_NAME, Unknown reason."
    }
    exit 1
}


Write-Host "Created new container: $CONTAINER_NAME"
Write-Host "___________________________________"