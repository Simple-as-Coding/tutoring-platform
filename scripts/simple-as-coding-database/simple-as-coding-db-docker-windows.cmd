@echo off
set CONTAINER_NAME=simpleascoding

rem Check if the container already exists, if so, delete it
docker ps -a --format "{{.Names}}" | findstr /r /c:"^%CONTAINER_NAME%$" 2>nul
if %errorlevel% equ 0 (
    echo Deleting old container: %CONTAINER_NAME%
    docker stop %CONTAINER_NAME% > nul 2>&1
    docker rm %CONTAINER_NAME% > nul 2>&1
)

rem Starting a new container
docker run -d ^
--name %CONTAINER_NAME% ^
-e POSTGRES_USER=postgres ^
-e POSTGRES_PASSWORD=root ^
-e POSTGRES_DB=simpleascoding ^
-p 5432:5432 ^
postgres 2^>^&1') do set "output=%%i"

if "%output%" neq "" (
    echo %output% | findstr /i "out of memory" >nul
    if %errorlevel% equ 0 (
        echo Error: Out of memory. Insufficient memory available.
        exit /b 1
    )

    echo %output% | findstr /i "conflict" >nul
    if %errorlevel% equ 0 (
        echo Error: Container name conflict. Container with the same name already exists.  Most likely the script encountered a problem with deleting an old container with the same name.
        exit /b 1
    )

    echo %output% | findstr /i "no such image" >nul
    if %errorlevel% equ 0 (
        echo Error: Image not found. The specified container image does not exist.
        exit /b 1
    )

    echo Error: Failed to create a new container: %CONTAINER_NAME%, Unknown reason
    exit /b 1
)

echo Created new container: %CONTAINER_NAME%