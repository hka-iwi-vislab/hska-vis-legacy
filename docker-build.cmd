set DOCKER_ACCOUNT=maxilambda

docker build -t webshop -f Dockerfile .
docker tag webshop %DOCKER_ACCOUNT%/webshop:latest
docker push %DOCKER_ACCOUNT%/webshop

docker build -t database -f DockerfileMySQL .
docker tag database %DOCKER_ACCOUNT%/database:latest
docker push %DOCKER_ACCOUNT%/database

docker build -t user -f usermanagement\Dockerfile usermanagement
docker tag user %DOCKER_ACCOUNT%/user:latest
docker push %DOCKER_ACCOUNT%/user
@REM
@REM docker build -t product -f productmanagement\Dockerfile productmanagement
@REM docker tag product %DOCKER_ACCOUNT%/product:latest
@REM docker push %DOCKER_ACCOUNT%/product
@REM
@REM docker build -t category -f categorymanagement\Dockerfile categorymanagement
@REM docker tag category %DOCKER_ACCOUNT%/category:latest
@REM docker push %DOCKER_ACCOUNT%/category
@REM
@REM docker build -t nginx -f reverse-proxy\Dockerfile reverse-proxy
@REM docker tag nginx %DOCKER_ACCOUNT%/nginx:latest
@REM docker push %DOCKER_ACCOUNT%/nginx