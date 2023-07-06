# tutoring-platform

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

## Table of contents
* [About The Project](#about-the-project)
* [Technologies](#technologies)
* [How to build](#how-to-build)
* [Launching The Project](#launching-the-project)
* [Roadmap](#roadmap)
* [Contributing](#contributing)
* [License](#license)

## About The Project
Platform for teachers &amp; students, to offer or search for tutoring.

* User registration with mail confirmation.
* Authorization and authentication.

## Technologies
* Java
* SpringBoot
* Maven
* Postgres
* Docker
* Lombok
* Swagger
* Log4j 2
* Git

## How to build
To setup and run this project:

```shell
git clone https://github.com/Simple-as-Coding/tutoring-platform
```
```shell
cd tutoring-platform
```

### 1. Install PostgreSQL Database
Choose the appropriate version of the script depending on the operating system to run database in docker container.
data for database configuration in Docker are taken from the src/main/resources/application.properties file.

or install local PostgreSQL database (name your database "simpleascoding") for the app to detect it.

#### 1.1. Windows:
and fire up docker desktop via command or GUI
```shell
docker
```
Wait for docker daemon in docker desktop to start before running the script below
in PowerShell
```shell
.\scripts\database-launch-starter\database-launch-starter.ps1
```
If you are getting an error related to the script execution policy, you can change it by running PowerShell as an administrator and typing:
Set-ExecutionPolicy RemoteSigned

#### 1.2. Linux:
The following command is valid for these operating system versions.
* Ubuntu 15.04 and newer
* Fedora
* CentOS 7 and newer
* Debian 8 and newer
* Arch Linux
* openSUSE 42.2 and newer
* RHEL 7 and newer
* SLES 12 and newer
```shell
sudo systemctl start docker
```
The following command is valid for these operating system versions.
* Ubuntu prior to 15.04
* Debian prior to 8
* CentOS 6 and older
* RHEL 6 and older
* SLES 11 and older
```shell
sudo service docker start 
```

The following commands are not version dependent
sometimes you need to give execute permissions to the script
```shell
sudo chmod +x scripts/database-launch-starter/database-launch-starter.bash
```
Run with root rights
```shell
sudo ./scripts/database-launch-starter/database-launch-starter.bash
```

### Launching The Project
After creating the database we install the project from the tutorial-platform directory.
Important! The project will not build without a database connection.

**path:** `/tutoring-platform/...`

#### 2.1. Building an Application
```shell
mvn clean install
```

#### 2.2. Running application

```shell
mvn spring-boot:run
```
After launching the project, the database should be supplemented with tables by hibernate

### 3. Launch MailServer
you need to run docker before running the script

#### 3.1 Windows
Run with root rights
```shell
.\scripts\mailserver-launch-starter\mailserver-launch-starter.ps1
```

#### 3.2 Linux
```shell
sudo chmod +x scripts/mailserver-launch-starter/mailserver-launch-starter.bash
```
Run with root rights
```shell
sudo ./scripts/mailserver-launch-starter/mailserver-launch-starter.bash
```

Mailbox --> 
http://localhost:1080/
default credentials: hello, hello (if it doesn't work then check application.properties spring.mail.username, spring.mail.password)

Swagger Documentation --> 
http://localhost:8080/swagger-ui/index.html
At the very end, we can enter the API documentation check by swagger or a tool with tools like POSTMAN for testing REST API

## Roadmap
- [ ] Implement core functionalities
- [ ] Expand database model
- [ ] Create documentation

## Contributing
For any information about contributions [check here][contributing-url].

## License
Distributed under the GPL-3.0 License. [Check here][license-url] for more information.

[contributors-shield]: https://img.shields.io/github/contributors/Simple-as-Coding/tutoring-platform.svg?style=for-the-badge
[contributors-url]: https://github.com/Simple-as-Coding/tutoring-platform/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Simple-as-Coding/tutoring-platform.svg?style=for-the-badge
[forks-url]: https://github.com/Simple-as-Coding/tutoring-platform/network/members
[stars-shield]: https://img.shields.io/github/stars/Simple-as-Coding/tutoring-platform.svg?style=for-the-badge
[stars-url]: https://github.com/Simple-as-Coding/tutoring-platform/stargazers
[issues-shield]: https://img.shields.io/github/issues/Simple-as-Coding/tutoring-platform.svg?style=for-the-badge
[issues-url]: https://github.com/Simple-as-Coding/tutoring-platform/issues
[license-shield]: https://img.shields.io/github/license/Simple-as-Coding/tutoring-platform.svg?style=for-the-badge
[license-url]: https://github.com/Simple-as-Coding/tutoring-platform/blob/main/LICENSE
[contributing-url]: https://github.com/Simple-as-Coding/tutoring-platform/wiki/Easy-way-to-start-contribute-our-project