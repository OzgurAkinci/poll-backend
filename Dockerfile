#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS mvn_image
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true

#
# Package stage
#
FROM openjdk:8-jre-slim AS jdk_image
COPY --from=mvn_image /home/app/target/poll-backend.jar poll-backend.jar
EXPOSE 8182
ENTRYPOINT ["java", "-jar", "/poll-backend.jar"]
