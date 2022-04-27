FROM openjdk:16-jdk-alpine

ARG JAR_FILE=build/libs/resourceservice-0.0.1.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]