# Etapa 1: Build usando Gradle y JDK
FROM gradle:9.0.0-jdk21-jammy AS build
WORKDIR /home/gradle/src

# Solo copiar archivos esenciales de build
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

# Copiar código fuente
COPY src src

# Construir el JAR sin tests para acelerar build
RUN ./gradlew bootJar --no-daemon -x test

# Etapa 2: Imagen liviana final
FROM gcr.io/distroless/java21-debian12
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /home/gradle/src/build/libs/Reportes.jar ./Reportes.jar

# Expose the application port
EXPOSE 8082

# Set the entrypoint to run the application
ENTRYPOINT ["java", "-jar", "Reportes.jar"]