FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml ./pom.xml
COPY services/pom.xml ./services/pom.xml

COPY services/CommentService/pom.xml ./services/CommentService/pom.xml
COPY services/CommentService/src ./services/CommentService/src

RUN mvn -q -B -f services/CommentService/pom.xml -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/services/CommentService/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
