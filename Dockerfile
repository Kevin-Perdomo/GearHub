FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copiar arquivos do Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download das dependências
RUN ./mvnw dependency:go-offline -B

# Copiar código fonte
COPY src src

# Build da aplicação
RUN ./mvnw package -DskipTests

# Estágio de runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar o JAR construído
COPY --from=build /app/target/*.jar app.jar

# Expor porta
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
