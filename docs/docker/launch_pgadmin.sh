#!/usr/bin/bash

sudo docker run \
 --name pgadmin \
 -e "POSTGRES_USER=postgres" \
 -e "PGADMIN_DEFAULT_PASSWORD=root" \
 -e "PGADMIN_DEFAULT_EMAIL=your_regiestered_email_address@gmail.com" \
 -p 5050:80 -d \
 dpage/pgadmin4
