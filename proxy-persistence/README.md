# Proxy Persistence
Este proyecto se encarga de persistir las solicitudes que ha sido realizadas por **proxy-service** con el fin de realizar estadisticas y analisis de las solicitudes realizadas por el proxy
## Configuración
### Requisitos
Para ejecutar y trabajar este proyecto, necesitarás tener instalados los siguientes elementos:
- **Java 17**
- **Gradle**
- **Mongo**

### Construir el proyecto
Este proyecto usa Gradle como herramienta de construcción. Puedes compilar y construir el proyecto con el siguiente comando:
```bash
  ./gradlew build -x test
```

### Ejecución local
Puedes ejecutar el proyecto de manera local después de haberlo construido:
```bash
  java -jar build/libs/proxy-persistence-0.0.1-SNAPSHOT.jar
```

### Ejecución de test
Para ejecutar los tests del proyecto, puedes usar el siguiente comando:
```bash
  ./gradlew test
```

### Ejecución con Docker
Para ejecutar el proyecto usando Docker y Docker Compose:
```bash
  docker-compose up --build
```

## Variables de entorno
Para ejecutar este proyecto puedes agregar las siguientes variables de entorno a su archivo `.env`

| env                     | descripcion                                                                       | default                                 |
|-------------------------|-----------------------------------------------------------------------------------|-----------------------------------------|
| `MONGODB_URI`           | host de mongo                                                                     | `mongodb://localhost:27017/persistence` |

## Stack tecnologico
Para el desarrollo de este proyecto se hizo uso de del framework `Spring boot 3.3.4` y `java 17`, para la persistencia de datos se hizo uso de `MongoDB`








