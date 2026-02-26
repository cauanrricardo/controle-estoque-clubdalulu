# ====== BUILD (compila o projeto) ======
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn -q -DskipTests package

# ====== RUN (roda o jar) ======
FROM eclipse-temurin:17-jre
WORKDIR /app

# Render passa a porta pela env PORT
ENV PORT=8080
EXPOSE 8080

# copia o jar gerado no build
COPY --from=build /app/target/*.jar app.jar

CMD ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]