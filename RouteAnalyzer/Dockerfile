# Use an official OpenJDK runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/RouteAnalyzer-1.0-SNAPSHOT-all.jar /app/app.jar

# Expose the port that the application will run on (optional)
EXPOSE 8080

# Mount the volume for the custom-parameters.yml file
# This assumes the file is at /path/to/custom-parameters.yml on the host machine
# and you want to mount it to /app/custom-parameters.yml inside the container
VOLUME ["/app/custom-parameters.yml"]

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
