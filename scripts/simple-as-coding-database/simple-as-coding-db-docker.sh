#!/bin/bash
CONTAINER_NAME="simpleascoding"

# Check if the container already exists, if so, delete it
if docker ps -a --format "{{.Names}}" | grep -q "^$CONTAINER_NAME$"; then
    echo "Deleting old container: $CONTAINER_NAME"
    docker stop $CONTAINER_NAME > /dev/null 2>&1
    docker rm $CONTAINER_NAME > /dev/null 2>&1
fi

# Starting a new container
docker run -d \
--name $CONTAINER_NAME \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=root \
-e POSTGRES_DB=simpleascoding \
-p 5432:5432 \
postgres
echo "Created new container: $CONTAINER_NAME"