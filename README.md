
# Tenpo Challenge
### Prerequisitos
* Apache Maven
* Docker

### Stack utilizado:

- Java 17
- Spring Boot Webflux
- JUnit y Mockito
- Redis
- Postgres
- RabbitMq

### Postman Collection:
Se incluye una collection de Postman y Enviroment de ejemplo con todos los endpoints en carpeta Postman

### Postgres:
Se incluye script para inicializar la Base de datos, al iniciar el contenedor.

### RabbitMQ:
Se incluye configuracion para crear queue, al iniciar el contenedor.

### Cómo ejecutar con docker
`docker-compose --profile prod up`

### Cómo ejecutar para debbuging
Ejecutar solo infra (postgres, redis, rabbitmq):

`docker-compose up`

Cambiar variables de entorno (solo para debbug):
```
POSTGRES_HOST=localhost
RABBIT_HOST=localhost
REDIS_HOST=localhost
```

### Guardado del historial de requests
Para este requerimiento se decidió agregar un webfilter para capturar todos los request y al finalizar se deja en una queue (RabbitMQ).
En este caso dentro del mismo microservicio se agrega un listener para tomar todos los request que lleguen a la queue de Rabbit y luego se procede al guardado en la base de datos.
