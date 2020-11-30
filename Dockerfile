FROM openjdk:8-jre-slim
COPY target/poll-backend.jar poll-backend.jar
EXPOSE 8182
ENTRYPOINT ["java", "-jar", "/poll-backend.jar"]
