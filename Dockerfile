FROM openjdk:16-jdk-alpine3.13
ARG JAR_FILE=martian-robots-*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar","spring-boot"]
