FROM openjdk:17
LABEL authors="nnh10052002@gmail.com"
COPY target/*.jar secureapi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/secureapi.jar"]