FROM maven:3.8.3-openjdk-17 as builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:17-alpine
COPY --from=builder /usr/src/app/target/tenpo-challenge-*.jar /usr/local/lib/tenpo-challenge.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/tenpo-challenge.jar"]