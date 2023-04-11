#!/bin/bash

mvn clean install -DskipTests

DOCKERFILE="Dockerfile"
IMAGE_NAME="demo/supercook-api:latest"
docker build . -t $IMAGE_NAME -f $DOCKERFILE
