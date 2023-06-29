#!/bin/bash

# Check if port 8080 is blocked
is_port_blocked=$(netstat -tuln | grep ":8080")

if [[ -n $is_port_blocked ]]; then
  echo "Port 8080 is blocked. Finding the process..."

  # Find the process IDs blocking port 8080
  pid_list=$(lsof -t -i :8080)

  if [[ -n $pid_list ]]; then
    echo "Processes blocking port 8080:"
    echo "$pid_list"

    # Kill the processes
    echo "Killing the processes..."
    kill $pid_list

    echo "Processes have been killed."
  else
    echo "No processes blocking port 8080 found."
  fi
else
  echo "Port 8080 is free."
fi