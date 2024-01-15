FROM openjdk:17-ea-11-jdk-slim
COPY . /app
WORKDIR /app
ENTRYPOINT ["./gradlew", "bootRun"]
