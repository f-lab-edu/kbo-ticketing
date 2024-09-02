FROM openjdk:17
ARG JAR_FILE=build/libs/kbo-ticketing-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} kbo-ticketing.jar
ENTRYPOINT ["java", \
 "-javaagent:/pinpoint/pinpoint-bootstrap-2.5.1.jar", \
 "-Dpinpoint.agentId=kbo-agent", \
 "-Dpinpoint.applicationName=kbo-ticketing", \
 "-jar", "/kbo-ticketing.jar"]