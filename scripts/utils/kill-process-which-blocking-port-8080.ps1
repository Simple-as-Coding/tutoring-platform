# Check if port 8080 is blocked
$is_port_blocked = Get-NetTCPConnection | Where-Object { $_.LocalPort -eq 8080 }

if ($is_port_blocked) {
    Write-Host "Port 8080 is blocked. Finding the process..."

    # Find the process IDs blocking port 8080
    $pid_list = (Get-NetTCPConnection | Where-Object { $_.LocalPort -eq 8080 }).OwningProcess

    if ($pid_list) {
        Write-Host "Processes blocking port 8080:"
        Write-Host $pid_list

        # Kill the processes
        Write-Host "Killing the processes..."
        Stop-Process -Id $pid_list

        Write-Host "Processes have been killed."
    }
    else {
        Write-Host "No processes blocking port 8080 found."
    }
}