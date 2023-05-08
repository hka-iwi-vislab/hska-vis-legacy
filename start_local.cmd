@echo off
docker compose -f docker-compose-local.yml build
docker compose -f docker-compose-local.yml up -d --remove-orphans
