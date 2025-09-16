FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml ./pom.xml
COPY services/pom.xml ./services/pom.xml

COPY services/ProfanityService/pom.xml ./services/ProfanityService/pom.xml
COPY services/ProfanityService/src ./services/ProfanityService/src

RUN mvn -q -B -f services/ProfanityService/pom.xml -DskipTests package

FROM eclipse-temurin:21-jre
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY otel/opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar

COPY --from=build /workspace/services/ProfanityService/target/*.jar app.jar
ENV JAVA_TOOL_OPTIONS="-javaagent:/otel/opentelemetry-javaagent.jar"

ENTRYPOINT ["java","-jar","/app/app.jar"]
