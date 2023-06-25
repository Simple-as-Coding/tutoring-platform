# tutoring-platform

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

## Table of contents
* [About The Project](#about-the-project)
* [Technologies](#technologies)
* [Getting Started](#getting-started)
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

## Installation and Setup
To setup and run this project:

```shell
git clone https://github.com/Simple-as-Coding/tutoring-platform
```
```shell
cd tutoring-platform
```

### 1. Install PostgreSQL Database
choose the appropriate version of the script depending on the operating system to run docker container.

or install local PostgreSQL database (name your database "simpleascoding").

#### 1.1. Windows: 
```shell
docker
```
wait for docker daemon in docker desktop to start before running the script below
```shell
call scripts/simple-as-coding-database/simple-as-coding-db-docker-windows.cmd
```
#### 1.2. Linux:
```shell
sudo systemctl start docker
```
```shell
chmod +x scripts/simple-as-coding-database/simple-as-coding-db-docker-linux.sh
```
```shell
./scripts/simple-as-coding-database/simple-as-coding-db-docker-linux.sh
```

## 2. launching the project
After creating the database we install the project from the tutorial-platform directory.
Important! The project will not build without a database connection.

2.1. installation
```shell
mvn clean install -T 1C
```

2.2. running

```shell
mvn spring-boot:run
```
after launching the project, the database should be supplemented with tables by hibernate

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