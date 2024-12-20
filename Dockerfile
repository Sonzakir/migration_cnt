
FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /app

COPY . .

# root projekt
RUN mvn clean install -DskipTests

# web projekt
RUN mvn clean package -DskipTests -pl web -am

# lightweight JDK image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/web/target/web-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]






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
