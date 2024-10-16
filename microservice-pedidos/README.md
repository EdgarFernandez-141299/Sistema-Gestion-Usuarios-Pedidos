# Documentación para la creación y ejecución del JAR del microservicio `microservice-pedidos`

## Requisitos previos

Asegúrate de tener instalados los siguientes requisitos antes de ejecutar la microservicio:

- **Java Development Kit (OpenJDK)** 21 o superior.
- **Maven** 3.9.6  o superior

## Pasos para crear y ejecutar el microservicio mediante el JAR:

### 1. Compilar y construir el JAR.
Desde el directorio raíz del proyecto, abre una terminal y ejecuta el siguiente comando para construir el JAR de la microservicio utilizando Maven:

```bash
mvn clean install
```

### 2. Verificación de la creación del JAR.
Si la construcción fue exitosa, deberías ver el archivo JAR generado en la siguiente ruta:

- ./target/microservice-pedidos-0.0.1-SNAPSHOT.jar

### 3. Ejecutar el microservicio.
Para ejecutar la microservicio, utiliza el siguiente comando desde el directorio raíz:

```bash
java -jar target/microservice-pedidos-0.0.1-SNAPSHOT.jar
```

### 4. Probar el microservicio.
Para realizar pruebas de la funcionalidad al servicio, se comparte la colección de "Postman" en la siguiente ruta:

- ./documentation/microservice-pedidos.postman_collection.json

## NOTAS:

**NOTA 1: Para la correcta funcionalidad, es necesario que esten desplegados ambos microservicios de forma local, ya que hay comunicación entre los mismos.**

**NOTA 2: Se creo y probo el "Dockerfile" que viene en raiz del proyecto, por si se requiere construir una imagen para el despliegue de los microservicios en contenedores, solo sera necesario antes de desplegar configurar las propiedades de los microservcios para que puedan comunicarse entre si.**

**NOTA 3: Se implemento Seguridad mediante "JWT" y trazabilidad de peticiones en ambos microservicios.**