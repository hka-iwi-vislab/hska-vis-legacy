# Build stage
FROM amazoncorretto:21-al2023-jdk as build

# Install Maven
RUN yum update && \
    yum install -y maven

# Set the working directory
WORKDIR /app

# Copy the Maven settings file and pom.xml
COPY pom.xml ./

# go-offline using the pom.xml
RUN mvn dependency:go-offline

# Copy the source code
COPY src/ src/

RUN mvn clean install -Dmaven.test.skip=true

# Run stage
FROM amazoncorretto:21-al2023-jdk

# Set the working directory
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port that the application will run on
EXPOSE 8081

# Run the application
CMD ["java", "-jar", "app.jar"]
