FROM openjdk:12-jdk-alpine
COPY ./build/libs/Console-0.0.1-SNAPSHOT.jar Console.jar
COPY configuration.properties .
ENTRYPOINT ["java","-jar","/Console.jar"]
