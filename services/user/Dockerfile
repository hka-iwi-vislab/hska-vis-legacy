# Build stage
FROM eclipse-temurin:11-jdk as build

# Install Maven
RUN apt-get update && \
    apt-get install -y maven

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
FROM eclipse-temurin:11-jre

# Set the working directory
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
