FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml ./pom.xml
COPY services/article-service/pom.xml ./services/article-service/pom.xml
COPY services/article-service/src ./services/article-service/src

RUN mvn -q -B -f services/article-service/pom.xml -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY otel/opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar
COPY --from=build /workspace/services/article-service/target/*-SNAPSHOT.jar app.jar
ENV JAVA_TOOL_OPTIONS="-javaagent:/otel/opentelemetry-javaagent.jar"


ENTRYPOINT ["java","-jar","/app/app.jar"]
