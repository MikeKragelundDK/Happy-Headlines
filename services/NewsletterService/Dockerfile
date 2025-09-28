FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml ./pom.xml
COPY services/pom.xml ./services/pom.xml

COPY services/NewsletterService/pom.xml ./services/NewsletterService/pom.xml
COPY services/NewsletterService/src ./services/NewsletterService/src

RUN mvn -q -B -f services/NewsletterService/pom.xml -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY otel/opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar

COPY --from=build /workspace/services/NewsletterService/target/*.jar app.jar
COPY newrelic/newrelic.jar /usr/local/newrelic/newrelic.jar
COPY newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml
ENV JAVA_TOOL_OPTIONS="-javaagent:/otel/opentelemetry-javaagent.jar -javaagent:/usr/local/newrelic/newrelic.jar"

ENTRYPOINT ["java","-jar","/app/app.jar"]
