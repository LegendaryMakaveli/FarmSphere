FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .

COPY core-module/pom.xml core-module/
COPY shared-infrastructure/pom.xml shared-infrastructure/
COPY auth-module/pom.xml auth-module/
COPY estate-module/pom.xml estate-module/
COPY farming-module/pom.xml farming-module/
COPY tool-module/pom.xml tool-module/
COPY marketplace-module/pom.xml marketplace-module/
COPY investment-module/pom.xml investment-module/
COPY notification-module/pom.xml notification-module/
COPY app/pom.xml app/

RUN mvn dependency:go-offline -B

COPY core-module/src core-module/src
COPY shared-infrastructure/src shared-infrastructure/src
COPY auth-module/src auth-module/src
COPY estate-module/src estate-module/src
COPY farming-module/src farming-module/src
COPY tool-module/src tool-module/src
COPY marketplace-module/src marketplace-module/src
COPY investment-module/src investment-module/src
COPY notification-module/src notification-module/src
COPY app/src app/src
FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S farmsphere && adduser -S farmsphere -G farmsphere

WORKDIR /app
COPY --from=build /app/app/target/*.jar farmsphere.jar
RUN chown farmsphere:farmsphere farmsphere.jar

USER farmsphere
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "farmsphere.jar"]