# ============================================================================
# Dockerfile - lista-tarefas-api
# ============================================================================

# ---- Build ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia só o pom.xml primeiro para cachear as dependências em camadas
# separadas do código-fonte (rebuild mais rápido quando só o código muda)
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

# ---- Runtime ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Roda como usuário não-root (boa prática de segurança em containers)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Usa o endpoint do Actuator que já está exposto no projeto
HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
    CMD wget -qO- http://localhost:8080/actuator/health | grep -q '"status":"UP"' || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
