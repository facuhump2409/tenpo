FROM gradle:6.6.1-jdk11 as cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY build.gradle.kts /home/gradle/kotlin-code/
WORKDIR /home/gradle/kotlin-code
RUN gradle clean build -i --stacktrace -x bootJar

FROM gradle:6.6.1-jdk11 as builder
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY . /usr/src/kotlin-code/
WORKDIR /usr/src/kotlin-code
RUN gradle bootJar -i --stacktrace

FROM openjdk:11-jre-slim
USER root
WORKDIR /usr/src/java-app
COPY --from=builder /usr/src/kotlin-code/build/libs/*.jar ./app.jar
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]
