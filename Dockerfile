# ---- Stage 1: Build ----
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Forzar UTF-8 en Maven para evitar MalformedInputException
ENV MAVEN_OPTS="-Dfile.encoding=UTF-8"

# Copiar pom y descargar dependencias primero (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código fuente
COPY src ./src

# Compilar y empaquetar sin tests (puedes activar tests en CI)
RUN mvn clean package -DskipTests -Dfile.encoding=UTF-8

# ---- Stage 2: Runtime ----
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

# Copiar JAR compilado
COPY --from=build /app/target/user-service-0.0.1-SNAPSHOT.jar ./user-service.jar

# Puerto expuesto
EXPOSE 8080

# Variables de entorno
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS=""

# Comando de arranque
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar user-service.jar"]
