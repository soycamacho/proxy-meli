# Proxy Service
El proyecto proxy-service se encarga de habilitar un endpoint como proxy para un host, ademas de ofrecer una API donde se establece la configuracion de acceso a la api de ese host
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
  java -jar build/libs/proxy-service-0.0.1-SNAPSHOT.jar
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

| env                     | descripcion                                                                       | default                                   |
|-------------------------|-----------------------------------------------------------------------------------|-------------------------------------------|
| `MONGODB_URI`           | host de mongo                                                                     | `mongodb://localhost:27017/proxy_service` |
| `API_URL`               | host al cual va redireccionar el proxy                                            | `https://api.mercadolibre.com`            |
| `PROXY_REJECTED_STATUS` | status http que responde el proxy cuando excede el limite de solicitudes al proxy | `423`                                     |
| `PERSISTENCE_API_URL`   | host del servicio donde se van a enviar las solicitudes realizadas                | `localhost:8081`                          |

## Stack tecnologico
Para el desarrollo de este proyecto se hizo uso de del framework `Spring boot 3.3.4` y `java 17`, para la persistencia de datos se hizo uso de `MongoDB`

## Endpoints
### Proxy
```bash
http://localhost:8080/proxy/{path}
```
| nombre | tipo de dato   | descripcion                     |
|--------|----------------|---------------------------------|
| path   | `String`       | path a direccionar por el proxy |
### Agregar permiso acceso
```bash
  curl --location 'http://localhost:8080/api/permissions' \
--header 'Content-Type: application/json' \
--data '{
    "ip": "{id}",
    "path": "{path}",
    "after": "{after}",
    "before": "{before}",
    "limit": {limit},
    "method": ["{}"]
}'
```
| nombre | tipo de dato                                         | descripcion                                                  |
|--------|------------------------------------------------------|--------------------------------------------------------------|
| ip     | `String`                                             | ip de origen que va acceder al proxy `opcional`              |
| path   | `String`                                             | Path del servicio que se va consumir por el proxy `opcional` |
| after  | `Date`                                               | Fecha y hora en que inicia el proxy `opcional`               |
| before | `Date`                                               | Fecha y hora en que termina el acceso al proxy `opcional`    |
| limit  | `Int`                                                | Numero maximo de solicitudes al proxy `opcional`             |
| method | `List[String]`<br/>`GET`,`POST`,`PUT`,`DELETE`, etc. | Metodo http que tiene acceso al proxy `opcional`             |
### Listar Permisos de acceso
```bash
  curl --location 'http://localhost:8080/api/permissions' \
--header 'Content-Type: application/json'
```
### Ver Permisos de acceso
```bash
  curl --location 'http://localhost:8080/api/permissions/{id}' \
--header 'Content-Type: application/json'
```
| nombre | tipo de dato    | descripcion   |
|--------|-----------------|---------------|
| id     | `String`        | id del acceso |
### Actualizar Permisos de acceso
```bash
  curl --location --request PUT 'http://localhost:8080/api/permissions/{id}' \
--header 'Content-Type: application/json' \
--data '{
    "ip": "{id}",
    "path": "{path}",
    "method": ["{method}"],
    "after": "{after}",
    "before": "{after}",
    "limit": {limit},
} '
```
| nombre | tipo de dato                                         | descripcion                                                  |
|--------|------------------------------------------------------|--------------------------------------------------------------|
| id     | `String`                                             | id del acceso                                                |
| ip     | `String`                                             | ip de o rigen que va acceder al proxy `opcional`              |
| path   | `String`                                             | Path del servicio que se va consumir por el proxy `opcional` |
| after  | `Date`                                               | Fecha y hora en que inicia el proxy `opcional`               |
| before | `Date`                                               | Fecha y hora en que termina el acceso al proxy `opcional`    |
| limit  | `Int`                                                | Numero maximo de solicitudes al proxy `opcional`             |
| method | `List[String]`<br/>`GET`,`POST`,`PUT`,`DELETE`, etc. | Metodo http que tiene acceso al proxy `opcional`             |
### Eliminar Permisos de acceso
```bash
  curl --location --request DELETE 'http://localhost:8080/api/permissions/{id}' \
--header 'Content-Type: application/json'
```
| nombre | tipo de dato    | descripcion   |
|--------|-----------------|---------------|
| id     | `String`        | id del acceso |
### Validar Permisos de acceso
```bash
  curl --location 'http://localhost:8080/api/permissions/validations?path={path}ip={ip}&method={method}&date={date}' \
--header 'Content-Type: application/json'
```
| nombre | tipo de dato                                   | descripcion                                                  |
|--------|------------------------------------------------|--------------------------------------------------------------|
| ip     | `String`                                       | ip de origen que va acceder al proxy `opcional`              |
| path   | `String`                                       | Path del servicio que se va consumir por el proxy `opcional` |
| date   | `Date`                                         | Fecha y hora que desea validar el acceso `opcional`          |
| method | `String`<br/>`GET`,`POST`,`PUT`,`DELETE`, etc. | Metodo http que tiene acceso `opcional`                      |










