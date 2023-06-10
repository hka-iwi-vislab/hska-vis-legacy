#!/bin/sh
if [ -z "$DOCKER_ACCOUNT" ]; then
    DOCKER_ACCOUNT=yoloswaglord1223
fi;
docker build -t webshop -f monolith/docker/Dockerfile monolith
docker tag webshop $DOCKER_ACCOUNT/webshop:latest
docker push $DOCKER_ACCOUNT/webshop

docker build -t webshop-database -f monolith/docker/DockerfileMySQL monolith
docker tag webshop-database $DOCKER_ACCOUNT/webshop-database:latest
docker push $DOCKER_ACCOUNT/webshop-database

docker build -t products products
docker tag products $DOCKER_ACCOUNT/products:latest
docker push $DOCKER_ACCOUNT/products

docker build -t categories categories
docker tag categories $DOCKER_ACCOUNT/categories:latest
docker push $DOCKER_ACCOUNT/categories

$SHELL