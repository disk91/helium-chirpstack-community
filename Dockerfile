FROM openjdk:21
COPY ./build/libs/Console-0.0.1-SNAPSHOT.jar Console.jar
COPY configuration.properties .
ENTRYPOINT ["java","-jar","/Console.jar"]
