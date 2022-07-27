# Uninstall Docker Engine

## Uninstall the Docker Engine, CLI, Containerd, and Docker Compose packages:

`sudo dnf remove docker-ce docker-ce-cli containerd.io docker-compose-plugin`

Images, containers, volumes, or customized configuration files on your host are not automatically removed. To delete all
images, containers, and volumes:

`sudo rm -rf /var/lib/docker`  
`sudo rm -rf /var/lib/containerd`

You must delete any edited configuration files manually.