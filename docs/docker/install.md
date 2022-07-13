# Docker

## Install Docker Engine

by [install docker engine](https://docs.docker.com/engine/install/)

## Install Docker Desktop

* [**Install Docker Desktop**](https://docs.docker.com/desktop/)
* [Install Docker Desktop on Linux](https://docs.docker.com/desktop/linux/install/)
* [Install Docker Desktop on Mac](https://docs.docker.com/desktop/mac/install/)
* [Install Docker Desktop on Windows](https://docs.docker.com/desktop/windows/install/)

## Post-installation steps for Linux

This section contains optional procedures for configuring Linux hosts to work better with Docker.  
[linux-postinstall](https://docs.docker.com/engine/install/linux-postinstall/)

## Configure Docker to start on boot

Most current Linux distributions (RHEL, CentOS, Fedora, Debian, Ubuntu 16.04 and higher) use systemd to manage which
services start when the system boots. On Debian and Ubuntu, the Docker service is configured to start on boot by
default. To automatically start Docker and Containerd on boot for other distros, use the commands below:

`sudo systemctl enable docker.service`  
`sudo systemctl enable containerd.service`

To disable this behavior, use disable instead.

`sudo systemctl disable docker.service`  
`sudo systemctl disable containerd.service`

## Configure default logging driver

Docker provides the capability to collect and view log data from all containers running on a host via a series of
logging drivers. The default logging driver, json-file, writes log data to JSON-formatted files on the host filesystem.
Over time, these log files expand in size, leading to potential exhaustion of disk resources.

To alleviate such issues, either configure the json-file logging driver to enable log rotation, use an alternative
logging driver such as the “local” logging driver that performs log rotation by default, or use a logging driver that
sends logs to a remote logging aggregator.

## Links
* [Install Docker Desktop](https://docs.docker.com/desktop/linux/install/)
* [Docker](https://zetcode.com/springboot/docker/)
