FROM java:8
FROM maven:alpine

WORKDIR /app

COPY . /app

RUN mvn -v
RUN mvn clean install -DskipTests

EXPOSE 8080
LABEL maintainer="deryadenizballi@gmail.com"
ARG JAR_FILE=target/rest-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} target/app.jar
ENTRYPOINT ["java","-jar","target/app.jar"]