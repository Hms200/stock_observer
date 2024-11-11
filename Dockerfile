FROM node:lts-alpine AS FRONT
WORKDIR /front
COPY ./front/ ./
RUN rm -rf dist
RUN rm -rf node_modules
RUN npm install
RUN npm run build

FROM openjdk:17-jdk-slim
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY src src
COPY --from=FRONT front/dist/ src/main/resources/static/
RUN chmod +x ./gradlew
RUN ./gradlew bootWar
COPY build/libs/*.war app.war

EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app.war"]
