set DOCKER_ACCOUNT=maxilambda

@REM docker build -t webshop -f docker\Dockerfile .
@REM docker tag webshop %DOCKER_ACCOUNT%/webshop:latest
@REM docker push %DOCKER_ACCOUNT%/webshop
@REM
@REM docker build -t database -f docker\DockerfileMySQL .
@REM docker tag database %DOCKER_ACCOUNT%/database:latest
@REM docker push %DOCKER_ACCOUNT%/database
@REM
@REM docker build -t user -f usermanagement\Dockerfile usermanagement
@REM docker tag user %DOCKER_ACCOUNT%/user:latest
@REM docker push %DOCKER_ACCOUNT%/user
@REM
@REM docker build -t product -f productmanagement\Dockerfile productmanagement
@REM docker tag product %DOCKER_ACCOUNT%/product:latest
@REM docker push %DOCKER_ACCOUNT%/product
@REM
@REM docker build -t category -f categorymanagement\Dockerfile categorymanagement
@REM docker tag category %DOCKER_ACCOUNT%/category:latest
@REM docker push %DOCKER_ACCOUNT%/category

docker build -t nginx -f reverse-proxy\Dockerfile reverse-proxy
docker tag nginx %DOCKER_ACCOUNT%/nginx:latest
docker push %DOCKER_ACCOUNT%/nginx