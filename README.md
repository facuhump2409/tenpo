# TenpoApplication

## Levantar con Docker
```
$ docker build -t tenpo-backend . && docker run -p 8080:8080 -d tenpo-backend
```
## Levantar con Docker-Compose
```
$ docker-compose build && docker-compose up
```

## Postman

En la carpeta postman se encuentra la collection con todos los requests a los siguientes servicios:
POST /users --> Crear un usuario
POST /users/tokens --> Loguearse o "crear un token de session"
DELETE /users/tokens --> Desloguearse
GET /requests?page= --> Obtener todas las solicitudes
GET /multiplication?number={}&anotherNumber={} --> Multiplicar dos numeros
