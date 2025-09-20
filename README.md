# Microservicio de Reportes - CrediYa

Microservicio responsable de la generación y gestión de reportes del ecosistema CrediYa.

## Funcionalidades

- Generación de reportes de rendimiento del negocio
- Reportes de préstamos aprobados y rechazados
- Métricas de solicitudes procesadas
- Dashboard de indicadores clave

## Tecnologías

- **Spring Boot 3.5.4** con WebFlux (Reactivo)
- **DynamoDB Local** para almacenamiento NoSQL
- **Swagger/OpenAPI** para documentación de API

## API Endpoints

- `GET /api/v1/reportes/prestamos` - Reporte de préstamos
- `GET /api/v1/reportes/solicitudes` - Reporte de solicitudes
- `GET /api/v1/reportes/rendimiento` - Reporte de rendimiento
- `GET /api/v1/reportes/dashboard` - Métricas del dashboard

## Variables de Entorno

```env
AWS_REGION=us-east-1
AWS_ACCESS_KEY_ID=dummy
AWS_SECRET_ACCESS_KEY=dummy
DYNAMODB_ENDPOINT_URL=http://dynamodb-local:8000
```

## Docker

El servicio se ejecuta en el puerto **8082** y se conecta a DynamoDB local en el puerto 8000.

## Arquitectura Clean Architecture

El proyecto implementa Clean Architecture con los siguientes módulos:

### Domain

Encapsula la lógica y reglas del negocio para generación de reportes.

### Usecases

Implementa los casos de uso para consultas y agregaciones de datos.

### Infrastructure

- **Driven Adapters**: Conexión a DynamoDB para consultas optimizadas
- **Entry Points**: APIs REST para consumo de reportes

### Application

Ensambla los módulos, resuelve dependencias y configura la aplicación.

**Los beans de los casos de uso se disponibilizan automáticamente gracias a '@ComponentScan'.**