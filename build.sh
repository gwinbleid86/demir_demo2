#!/bin/bash

docker build -t first_app ./minimalHttpServer/
docker build -t second_app ./second_project/
docker compose up -d
