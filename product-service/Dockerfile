ARG BUILD_IMAGE=maven:3.8.6-eclipse-temurin-11-alpine
ARG RUNTIME_IMAGE=openjdk:11-jre-slim-buster

FROM ${BUILD_IMAGE} as dependencies
ENV APP_HOME=/
WORKDIR $APP_HOME

COPY pom.xml $APP_HOME
ADD src $APP_HOME/src
RUN mvn -f pom.xml clean package install

FROM ${RUNTIME_IMAGE}
ENV ARTIFACT_NAME=*.jar
ENV PROFILE=dev

EXPOSE 8091


COPY --from=dependencies /target/$ARTIFACT_NAME /app/app.jar
CMD ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "/app/app.jar"]