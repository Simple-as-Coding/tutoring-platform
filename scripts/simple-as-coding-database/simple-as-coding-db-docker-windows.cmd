@echo off
echo "___________________________________"
echo "*      DATABASE SETUP SCRIPT      *"
echo "___________________________________"

rem Check if the required environment variables are set
IF "%TUTORING_PLATFORM_DB_USERNAME%"=="" || "%TUTORING_PLATFORM_DB_PASSWORD%"=="" || "%TUTORING_PLATFORM_DB_CONTAINER_NAME%"=="" (
    echo Required environment variables are not set. Trying to load from variables.env ...

    rem Load ..\..\variables.env
    IF EXIST ..\..\variables.env (
        for /f "usebackq tokens=1* delims== " %%A in (..\..\variables.env) do (
            SET "%%A=%%B"
        )
        echo SUCCESS: variables.env is loaded.
    ) else (
        echo Error: variables.env file not found.
        exit /b 1
    )
)

rem Assigning environment variables to internal script variables
SET "CONTAINER_NAME=%TUTORING_PLATFORM_DB_CONTAINER_NAME%"
SET "USERNAME=%TUTORING_PLATFORM_DB_USERNAME%"
SET "PASSWORD=%TUTORING_PLATFORM_DB_PASSWORD%"

rem Check if the required environment variables are set
IF "%TUTORING_PLATFORM_DB_USERNAME%"=="" (
    echo Error: Required environment variables are not set.
    exit /b 1
)

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
-e POSTGRES_USER=%USERNAME% ^
-e POSTGRES_PASSWORD=%PASSWORD% ^
-e POSTGRES_DB=%CONTAINER_NAME% ^
-p 5432:5432 ^
postgres 2^>^&1') do set "output=%%i"

rem Checking error codes and displaying the appropriate message based on it
if "%output%" neq "" (
    echo %output% | findstr /i "out of memory" >nul
    if %errorlevel% equ 0 (
        echo Error: Out of memory. Insufficient memory available.
        exit /b 1
    )

    echo %output% | findstr /i "conflict" >nul
    if %errorlevel% equ 0 (
        echo Error: Container name conflict. Container with the same name already exists. Most likely the script encountered a problem with deleting an old container with the same name.
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

echo Created new container: %CONTAINER_NAME%.