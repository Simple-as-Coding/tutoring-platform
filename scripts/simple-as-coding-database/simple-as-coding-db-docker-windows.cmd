@echo off
set CONTAINER_NAME=simpleascoding

REM Check if the container already exists, if so, delete it
docker ps -a --format "{{.Names}}" | findstr /r /c:"^%CONTAINER_NAME%$" 2>nul
if %errorlevel% equ 0 (
    echo Deleting old container: %CONTAINER_NAME%
    docker stop %CONTAINER_NAME% > nul 2>&1
    docker rm %CONTAINER_NAME% > nul 2>&1
)

REM Starting a new container
docker run -d ^
--name %CONTAINER_NAME% ^
-e POSTGRES_USER=postgres ^
-e POSTGRES_PASSWORD=root ^
-e POSTGRES_DB=simpleascoding ^
-p 5432:5432 ^
postgres

echo Created new container: %CONTAINER_NAME%