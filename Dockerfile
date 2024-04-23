FROM openjdk:17
ARG JAR_FILE=build/libs/kbo-ticketing-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} kbo-ticketing.jar
ENTRYPOINT ["java","-jar","/kbo-ticketing.jar"]