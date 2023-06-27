#!/bin/bash

echo "___________________________________"
echo "*      DATABASE SETUP SCRIPT      *"
echo "___________________________________"

# Check if the required environment variables are set
if [[ -z "${TUTORING_PLATFORM_DB_USERNAME}" || -z "${TUTORING_PLATFORM_DB_PASSWORD}" || -z "${TUTORING_PLATFORM_DB_CONTAINER_NAME}" ]]; then
    echo "Required environment variables are not set. Trying to load from variables.env ..."

    # Load ../../variables.env
    if [ -f ../../variables.env ]; then
        export $(cat ../../variables.env | xargs)
        echo "SUCCESS: variables.env is loaded."
    else
        echo "Error: variables.env file not found."
        exit 1
    fi
fi

# Assigning environment variables to internal script variables
CONTAINER_NAME=$TUTORING_PLATFORM_DB_CONTAINER_NAME
USERNAME=$TUTORING_PLATFORM_DB_USERNAME
PASSWORD=$TUTORING_PLATFORM_DB_PASSWORD

# Check if the container already exists, if so, delete it
if docker ps -a --format "{{.Names}}" | grep -q "^$CONTAINER_NAME$"; then
    echo "Deleting old container: $CONTAINER_NAME"
    docker stop $CONTAINER_NAME > /dev/null 2>&1
    docker rm $CONTAINER_NAME > /dev/null 2>&1
fi

# Starting a new container
output=$(docker run -d \
--name $CONTAINER_NAME \
-e POSTGRES_USER=$USERNAME \
-e POSTGRES_PASSWORD=$PASSWORD \
-e POSTGRES_DB=$CONTAINER_NAME \
-p 5432:5432 \
postgres 2>&1)

# Checking error codes and displaying the appropriate message based on it
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