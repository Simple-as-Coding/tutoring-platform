@echo off

rem # Run Postgres image
rem # Parameters:
rem # --name  Assign a name to the container! Not the name of database or w/e else
rem # -e      POSTGRES_USER
rem # -e      POSTGRES_PASSWORD
rem # -p      port host_port:docker_port
rem # -v      Bind mount a volume
rem # -d      Run container in background and print container ID
docker run --name docker-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -p 5432:5432 -v postgresqldata:/data/db -d postgres


rem # List containers
rem # Parameters:
rem # -a      List containers
docker ps -a

rem # Docker stop container
rem # sudo docker stop <id container from the list:  docker ps -a>

rem # Docker remove container
rem # sudo docker rm <id container from the list:  docker ps -a>
