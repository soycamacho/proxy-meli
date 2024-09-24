# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos necesarios
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Construir el proyecto
RUN ./gradlew build -x test

# Exponer el puerto del servicio
EXPOSE 8081

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "build/libs/proxy-persistence-0.0.1-SNAPSHOT.jar"]
