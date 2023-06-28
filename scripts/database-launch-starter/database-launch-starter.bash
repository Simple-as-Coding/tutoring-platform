#!/bin/bash

echo "___________________________________"
echo "*      DATABASE SETUP SCRIPT      *"
echo "___________________________________"

# Load application.properties
application_properties_dir="../../src/main/resources/application.properties"
if [ -f "$application_properties_dir" ]; then
    CONTAINER_NAME=$(grep "spring.datasource.url" $application_properties_dir | cut -d'=' -f2 | awk -F/ '{print $NF}')
    USERNAME=$(grep "spring.datasource.username" $application_properties_dir  | cut -d'=' -f2)
    PASSWORD=$(grep "spring.datasource.password" $application_properties_dir  | cut -d'=' -f2)
    PORT=$(grep "spring.datasource.url" $application_properties_dir | cut -d'/' -f3 | cut -d':' -f2)
    echo "SUCCESS: variables from application.properties are loaded."
else
    echo "Error: application.properties file not found."
    exit 1
fi

# Checking if docker daemon is running
if ! docker info > /dev/null 2>&1; then
    echo "Error: Failed to connect to Docker daemon. Please check if Docker daemon is running and accessible."
    echo "To solve this problem, try using the command depending on your system:"
    echo "    -> sudo systemctl start docker"
    echo "    -> sudo service docker start"
    echo "and start script again."
    exit 1
fi

# Check if the container already exists, if so, delete it
if docker ps -a --format "{{.Names}}" | grep -q "^$CONTAINER_NAME$"; then
    echo "Deleting old container: $CONTAINER_NAME"
    docker stop "$CONTAINER_NAME" > /dev/null 2>&1
    docker rm "$CONTAINER_NAME" > /dev/null 2>&1
fi

# Starting a new container
output=$(docker run -d \
--name "$CONTAINER_NAME" \
-e POSTGRES_USER="$USERNAME" \
-e POSTGRES_PASSWORD="$PASSWORD" \
-e POSTGRES_DB="$CONTAINER_NAME" \
-p "$PORT":5432 \
postgres 2>&1)

# Checking error codes and displaying the appropriate message based on it
exit_code=$?
if [ $exit_code -ne 0 ]; then
    if [[ $output == *"out of memory"* ]]; then
        echo "Error: Out of memory. Insufficient memory available."
    elif [[ $output == *"name conflict"* ]]; then
        echo "Error: Container name conflict. Container with the same name already exists. Most likely the script encountered a problem with deleting an old container with the same name."
    elif [[ $output == *"No such image"* ]]; then
        echo "Error: Image not found. The specified container image does not exist."
    elif [[ $output == *"Cannot connect to the Docker daemon "* ]]; then
        echo "Error: Docker daemon was running at the start of the script but is not responding at the moment."
    elif [[ $output == *"port is already allocated"* ]]; then
        echo "Error: Port conflict. The specified port is already allocated by another container."
    else
        echo "Error: Failed to create a new container: $CONTAINER_NAME, Unknown reason."
    fi
    exit 1
fi

echo "Created new container: $CONTAINER_NAME."
echo "___________________________________"