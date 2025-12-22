# 빌드용 이미지
FROM gradle:8.5-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

# test 생략 옵션 추가
RUN gradle build -x test --no-daemon

# 실행용 이미지
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
