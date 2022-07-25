FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /home/spring

#ARG JAR_FILE=build/libs/api-0.0.1-SNAPSHOT.jar
COPY ./build/libs/api-0.0.1-SNAPSHOT.jar /home/spring/application.jar

EXPOSE 8080

CMD ["java", "-jar", "/home/spring/application.jar"]