# Etapa 1: Construcción del JAR
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml y el código fuente
COPY pom.xml .
COPY src ./src

# Ejecutar Maven para compilar el JAR
RUN mvn clean package -DskipTests

# Etapa 2: Optimización de la imagen
FROM eclipse-temurin:21.0.3_9-jdk-alpine AS openjdk-21

# Instalar binutils
RUN apk add --no-cache binutils

# Crear una JRE optimizada
RUN $JAVA_HOME/bin/jlink \
         --verbose \
         --add-modules ALL-MODULE-PATH \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /jreoptimized

# Etapa 3: Imagen final
FROM alpine:latest

# Configurar JAVA_HOME y PATH
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Copiar la JRE optimizada
COPY --from=openjdk-21 /jreoptimized $JAVA_HOME

# Configurar la zona horaria
ENV TZ=America/Mexico_City

# Copiar el JAR generado desde la etapa de construcción
COPY --from=build /app/target/microservice-usuarios-0.0.1-SNAPSHOT.jar /app/

# Establecer el directorio de trabajo
WORKDIR /app

# Exponer el puerto
EXPOSE 8080

# Definir el comando de inicio
ENTRYPOINT ["/jre/bin/java","-jar","microservice-usuarios-0.0.1-SNAPSHOT.jar"]