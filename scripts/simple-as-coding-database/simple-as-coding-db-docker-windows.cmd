@echo off
echo "___________________________________"
echo "*      DATABASE SETUP SCRIPT      *"
echo "___________________________________"

rem Load application.properties
set "application_properties_dir=../../src/main/resources/application.properties"
if exist "%application_properties_dir%" (
    for /F "tokens=2 delims==" %%A in ('findstr "spring.datasource.url" "%application_properties_dir%"') do (
        set "CONTAINER_NAME=%%A"
    )
    for /F "tokens=2 delims==" %%B in ('findstr "spring.datasource.username" "%application_properties_dir%"') do (
        set "USERNAME=%%B"
    )
    for /F "tokens=2 delims==" %%C in ('findstr "spring.datasource.password" "%application_properties_dir%"') do (
        set "PASSWORD=%%C"
    )
    echo SUCCESS: variables from application.properties are loaded.
) else (
    echo Error: application.properties file not found.
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
echo "___________________________________"