#!/bin/bash

# Run Postgres image
# Parameters:
# --name  Assign a name to the container! Not the name of database or w/e else
# -e      POSTGRES_USER
# -e      POSTGRES_PASSWORD
# -p      port host_port:docker_port
# -v      Bind mount a volume
# -d      Run container in background and print container ID
sudo docker run --name docker-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -p 5432:5432 -v postgresqldata:/data/db -d postgres

# List containers
# Parameters:
# -a      List containers
sudo docker ps -a

# Docker stop container
# sudo docker stop <id container from the list:  docker ps -a>

# Docker remove container
# sudo docker rm <id container from the list:  docker ps -a>
