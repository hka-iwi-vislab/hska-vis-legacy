FROM amazoncorretto:21-al2023-jdk as build

# Install Maven
RUN yum update && yum install -y maven

#WORKDIR /app

COPY ./pom.xml ./pom.xml
COPY ./src ./src
COPY ./conf ./conf
RUN mvn clean package

FROM tomcat:9-jre8
COPY --from=builder /target/EShop-1.0.0.war /usr/local/tomcat/webapps/
COPY ./conf/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml

EXPOSE 8080
