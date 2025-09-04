FROM eclipse-temurin:21-jdk

LABEL maintainer="kragelund-wow@hotmail.com"

WORKDIR /app

COPY target/Happy-Headlines-ArticleService-0.0.1-SNAPSHOT.jar /app/Happy-Headlines-ArticleService-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "Happy-Headlines-ArticleService-0.0.1-SNAPSHOT.jar"]