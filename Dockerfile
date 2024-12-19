

#FROM maven:3.9.9-eclipse-temurin-21 AS builder
#WORKDIR /app
#COPY web .
#RUN mvn clean package -DskipTests
#
#FROM eclipse-temurin:21
#WORKDIR /app
#COPY --from=builder /app/target/*.jar app.jar
#ENTRYPOINT ["java" , "-jar" , "app.jar"]
#


FROM maven:3.8.5-openjdk-17 AS builder

# Set working directory
WORKDIR /app

# Copy the full project
COPY . .

# Build the entire project
RUN mvn clean install -DskipTests

# Build the web project
RUN mvn clean package -DskipTests -pl web -am

# Use a lightweight JDK image to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/web/target/web-1.0-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
