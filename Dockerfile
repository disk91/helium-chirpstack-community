FROM eclipse-temurin:24-jre
COPY ./build/libs/Console-0.0.1-SNAPSHOT.jar Console.jar
COPY configuration.properties .
ENTRYPOINT ["java","-jar","/Console.jar"]
