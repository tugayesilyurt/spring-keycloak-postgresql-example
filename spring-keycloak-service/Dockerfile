FROM maven:3.6.3 AS maven
LABEL MAINTAINER="htyesilyurt@gmail.com"

WORKDIR /opt/spring-keycloak-service
COPY . /opt/spring-keycloak-service
RUN mvn clean install 

# For Java 11, 
FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=spring-keycloak-service.jar

WORKDIR /opt/spring-keycloak-service

COPY --from=maven /opt/spring-keycloak-service/target/${JAR_FILE} /opt/spring-keycloak-service/

ENTRYPOINT ["java","-jar","spring-keycloak-service.jar"]