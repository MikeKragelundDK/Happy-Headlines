FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml ./pom.xml
COPY services/pom.xml ./services/pom.xml
COPY services/article-service/pom.xml ./services/article-service/pom.xml
COPY services/article-service/src ./services/article-service/src

RUN mvn -q -DskipTests -pl services/article-service -am package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/services/article-service/target/*-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
