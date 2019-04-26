FROM java:8
FROM maven:alpine

WORKDIR /app

COPY . /app

RUN mvn -v
RUN mvn clean install -DskipTests

EXPOSE 8080
LABEL maintainer="yusufcancelik@hotmail.com"
ENTRYPOINT ["java","-jar","target/backend-0.0.1-SNAPSHOT.jar"]