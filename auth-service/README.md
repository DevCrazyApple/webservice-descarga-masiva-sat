# Microservicio: Spring Boot + MongoDB + Redis (Arquitectura Hexagonal)

## 🧱 Arquitectura

Esta aplicación implementa una arquitectura **hexagonal** (Ports & Adapters) con integración a:

- **MongoDB**: persistencia con Spring Mongo
- **Redis**: database en Memoria NoSQL
- **Spring Boot**: motor principal de la aplicación
- **Gradle**: herramienta de build
- **Docker + Docker Compose**: para orquestación local

---

## 📦 Estructura de paquetes

```
├── java
│   └── com
│       └── ws
│           └── auth_service
│               ├── application
│               │   └── service
│               │       └── AuthService.java
│               ├── AuthServiceApplication.java
│               ├── domain
│               │   ├── model
│               │   │   └── AuthModel.java
│               │   └── port
│               │       ├── inbound
│               │       │   └── TokenGeneratorIn.java
│               │       └── outbound
│               │           └── TokenGeneratorOut.java
│               └── infrastructure
│                   ├── adapter
│                   │   ├── redis
│                   │   │   └── TokenCacheAdapter.java
│                   │   └── TokenGenerateAdapter.java
│                   ├── client
│                   │   ├── builder
│                   │   │   └── AuthXmlBuilder.java
│                   │   ├── config
│                   │   │   └── SoapClientConfig.java
│                   │   ├── parser
│                   │   │   └── AuthResponseParser.java
│                   │   ├── SatAuthenticationService.java
│                   │   ├── SoapClient.java
│                   │   └── util
│                   │       ├── CryptoUtils.java
│                   │       └── XmlUtils.java
│                   ├── config
│                   │   ├── GlobalExceptionHandler.java
│                   │   └── RedisConfig.java
│                   ├── controller
│                   │   └── AuthController.java
│                   ├── dto
│                   │   ├── AuthTokenResponse.java
│                   │   ├── ErrorResponse.java
│                   │   ├── GetTokenRequest.java
│                   │   └── TokenGenerateRequest.java
│                   ├── entities
│                   │   └── AuthEntity.java
│                   └── repository
│                       ├── AuthCustomRepositoryImpl.java
│                       ├── AuthCustomRepository.java
│                       └── AuthRepository.java
├── resources
│   ├── application.properties
│   ├── static
│   └── templates
```