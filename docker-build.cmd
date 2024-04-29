set DOCKER_ACCOUNT=maxilambda

docker build -t webshop -f docker\Dockerfile .
docker tag webshop %DOCKER_ACCOUNT%/webshop:latest
docker push %DOCKER_ACCOUNT%/webshop

docker build -t database -f docker\DockerfileMySQL .
docker tag database %DOCKER_ACCOUNT%/database:latest
docker push %DOCKER_ACCOUNT%/database

docker build -t user -f usermanagement\Dockerfile usermanagement
docker tag user %DOCKER_ACCOUNT%/user:latest
docker push %DOCKER_ACCOUNT%/user

docker build -t product -f productmanagement\Dockerfile productmanagement
docker tag product %DOCKER_ACCOUNT%/product:latest
docker push %DOCKER_ACCOUNT%/product

docker build -t category -f categorymanagement\Dockerfile categorymanagement
docker tag category %DOCKER_ACCOUNT%/category:latest
docker push %DOCKER_ACCOUNT%/category

docker build -t nginx -f reverse-proxy\Dockerfile reverse-proxy
docker tag nginx %DOCKER_ACCOUNT%/nginx:latest
docker push %DOCKER_ACCOUNT%/nginx
pause