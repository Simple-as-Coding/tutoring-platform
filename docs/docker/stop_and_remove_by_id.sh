#!/usr/bin/bash

function print_usage() {
  echo "Usage:"
  echo ""
  echo "  stop_and_remove_by_id.sh container_id"
  echo ""
  echo "container_ids - you can list by executing:"
  echo ""
  echo " sudo docker ps -a"
}

function list_docker_containers () {
    sudo docker ps -a
}

function stop_container () {
    sudo docker stop $1
}

function remove_container () {
    sudo docker rm $1
}

function quit {
    exit
}


# if [ -n "$1" ]; then
if [ -z "$1" ]; then
  print_usage
  list_docker_containers
else
  stop_container
  remove_container
fi
exit


