# Etapa 1: Build con Gradle
FROM gradle:8.14.2-jdk17 AS build
WORKDIR /home/gradle/project

# Copiar archivos del proyecto y asegurar permisos
COPY --chown=gradle:gradle . .
#COPY --chown=gradle:gradle gradle.properties ./gradle.properties

# Crear un caché aislado y compilar el JAR con Spring Boot
RUN mkdir -p /home/gradle/gradle-cache && chown gradle:gradle /home/gradle/gradle-cache \
  && gradle clean bootJar \
     --no-daemon \
     --no-parallel \
     --gradle-user-home /home/gradle/gradle-cache \
     --stacktrace \
     --info

# Etapa 2: Imagen final con JDK para ejecutar el .jar
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copiar el JAR desde la etapa anterior
COPY --from=build /home/gradle/project/build/libs/status-service.jar app.jar

# Puerto por defecto de Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-Duser.timezone=America/Mexico_City", "-jar", "app.jar"]
