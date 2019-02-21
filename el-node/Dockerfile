FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} el_node.jar
ENTRYPOINT  ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/el_node.jar"]