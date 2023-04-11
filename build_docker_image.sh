#!/bin/bash

mvn clean install -DskipTests

DOCKERFILE="Dockerfile"
IMAGE_NAME="demo/recipe-manager-api:latest"
docker build . -t $IMAGE_NAME -f $DOCKERFILE
