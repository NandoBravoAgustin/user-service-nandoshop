# Microservicio de Auntenticacion 

Este proyecto es una aplicación Spring Boot que incluye conexión a MySQL con **HikariCP**, validación de conexión al inicio y monitoreo con **Spring Boot Actuator**.

## 🔹 Monitoreo con Actuator

La aplicación expone un endpoint de salud para verificar el estado general y de la base de datos.


## Estado de la conexión de la base de datos
### Endpoint y curl
```http
GET /actuator/health

curl -s http://localhost:8080/actuator/health | jq
```
### ✅ Ejemplo cuando la aplicación funciona
```json 
{"status":"UP"}
```
### ❌ Ejemplo cuando la base de datos está caída
```json 
{"status":"DOWN"}
```

## CPU Usage
### Curl
```http
curl -s http://localhost:8080/actuator/metrics/system.cpu.usage | jq
```
## Memoria usada
### Curl
```http
curl -s http://localhost:8080/actuator/metrics/jvm.memory.used | jq
```
## Memoria máxima
### Curl
```http
curl -s http://localhost:8080/actuator/metrics/jvm.memory.max | jq
```

## Variables de entorno (solo en local disponible)
```http
curl -s http://localhost:8080/actuator/env | jq
curl -s http://localhost:8080/actuator/env/{property} | jq
```

## Loggers
```http
curl -s http://localhost:8080/actuator/env | jq
curl -s http://localhost:8080/actuator/env/{property} | jq
```
    