# B1: Build bằng Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests


# B2: Chạy app
FROM eclipse-temurin:17-jdk

WORKDIR /app

# copy file jar từ bước build
COPY --from=build /app/target/*.jar app.jar

# dùng PORT của Render
ENV PORT=8080

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]