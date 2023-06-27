#!/bin/bash
CONTAINER_NAME="simpleascoding"

# Check if the container already exists, if so, delete it
if docker ps -a --format "{{.Names}}" | grep -q "^$CONTAINER_NAME$"; then
    echo "Deleting old container: $CONTAINER_NAME"
    docker stop $CONTAINER_NAME > /dev/null 2>&1
    docker rm $CONTAINER_NAME > /dev/null 2>&1
fi

# Starting a new container
output=$(docker run -d \
--name $CONTAINER_NAME \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=root \
-e POSTGRES_DB=simpleascoding \
-p 5432:5432 \
postgres 2>&1)

exit_code=$?
if [ $exit_code -ne 0 ]; then
    if [[ $output == *"out of memory"* ]]; then
        echo "Error: Out of memory. Insufficient memory available."
    elif [[ $output == *"conflict"* ]]; then
        echo "Error: Container name conflict. Container with the same name already exists. Most likely the script encountered a problem with deleting an old container with the same name."
    elif [[ $output == *"No such image"* ]]; then
        echo "Error: Image not found. The specified container image does not exist."
    else
        echo "Error: Failed to create a new container: $CONTAINER_NAME, Unknown reason."
    fi
    exit 1
fi

echo "Created new container: $CONTAINER_NAME."