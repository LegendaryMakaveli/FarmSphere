# ── Stage 1: Build ─────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# copy parent pom
COPY pom.xml .

# copy all module poms
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

# download dependencies
RUN mvn dependency:go-offline -B

# copy all source code
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

# build — if this fails the error will show exactly why
RUN mvn clean package -DskipTests -B --no-transfer-progress

# verify JAR exists — catches the error early with a clear message
RUN ls -la app/target/ && \
    test -f app/target/*.jar || \
    (echo "JAR not found in app/target/" && exit 1)

# ── Stage 2: Run ───────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S farmsphere && adduser -S farmsphere -G farmsphere

WORKDIR /app

# copy JAR from build stage
COPY --from=build /app/app/target/*.jar farmsphere.jar

RUN chown farmsphere:farmsphere farmsphere.jar

USER farmsphere

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "farmsphere.jar"]