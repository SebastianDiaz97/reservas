# 📅 API REST de Gestión de Reservas

API REST desarrollada con **Java y Spring Boot** para gestionar reservas entre usuarios y profesionales.

El sistema permite administrar profesionales, servicios, disponibilidades horarias y reservas, incorporando reglas de negocio para calcular turnos disponibles y evitar conflictos de horario.

## 🚀 Características

* Gestión de usuarios.
* Gestión de profesionales.
* Gestión de servicios.
* Configuración de disponibilidades semanales por profesional.
* Asociación entre profesionales y servicios.
* Consulta de turnos disponibles.
* Creación y cancelación de reservas.
* Validación de solapamientos entre reservas.
* Borrado lógico de registros mediante estado activo/inactivo.
* Manejo centralizado de excepciones.
* Configuración de seguridad mediante Spring Security.
* Base de datos MySQL.
* Ejecución de la base de datos mediante Docker Compose.

---

## 🛠️ Tecnologías

* **Java 21**
* **Spring Boot 4.0.0**
* **Spring Web MVC**
* **Spring Data JPA**
* **Spring Security**
* **MySQL 8.0**
* **Lombok**
* **Maven**
* **Docker / Docker Compose**
* **JJWT** — dependencia preparada para implementación de autenticación mediante JWT

---

## 🏗️ Arquitectura

El proyecto utiliza una arquitectura organizada por capas:

```text
src/main/java/com/reservas/api/apireservas
│
├── controller/       # Endpoints REST
├── service/          # Lógica de negocio
├── repository/       # Acceso a datos mediante Spring Data JPA
├── model/            # Entidades JPA y enums
├── dto/              # Objetos de transferencia de datos
├── mapper/           # Conversión entre entidades y DTO
├── exception/        # Excepciones y manejo global de errores
└── security/         # Configuración de Spring Security
```

La separación de responsabilidades permite mantener independientes los controladores, la lógica de negocio y el acceso a datos.

---

## 🗂️ Modelo de datos

Las principales entidades del sistema son:

| Entidad                 | Descripción                                      |
| ----------------------- | ------------------------------------------------ |
| `User`                  | Usuario que puede realizar reservas              |
| `Professional`          | Profesional que ofrece servicios                 |
| `Provision`             | Servicio que puede ser reservado                 |
| `ProfessionalProvision` | Relación entre profesionales y servicios         |
| `Availability`          | Disponibilidad horaria semanal de un profesional |
| `Booking`               | Reserva realizada por un usuario                 |

### Relaciones principales

```text
User
 │
 └──< Booking >── Professional
                     │
                     ├──< Availability
                     │
                     └──< ProfessionalProvision >── Provision
                                                        │
                                                        └── Booking
```

---

## 🧠 Reglas de negocio

### Consulta de disponibilidad

El endpoint:

```http
GET /booking/available
```

calcula los turnos disponibles para un profesional y servicio en una fecha determinada.

El sistema:

1. Obtiene la duración del servicio.
2. Verifica la disponibilidad del profesional para ese día de la semana.
3. Genera las franjas horarias correspondientes.
4. Consulta las reservas existentes.
5. Descarta los horarios que se solapan con reservas activas.
6. Devuelve los turnos disponibles.

Por ejemplo, si un servicio dura 30 minutos y el profesional tiene disponibilidad entre las 09:00 y las 12:00, el sistema puede generar franjas como:

```text
09:00 - 09:30
09:30 - 10:00
10:00 - 10:30
...
```

excluyendo aquellas que estén ocupadas.

### Creación de reservas

Al crear una reserva, el sistema valida:

* Que los parámetros requeridos estén presentes.
* Que el usuario exista.
* Que el profesional exista.
* Que el servicio exista.
* Que el profesional ofrezca el servicio seleccionado.
* Que exista disponibilidad para ese día.
* Que el horario solicitado no se solape con otra reserva activa.

Si existe un conflicto de horario, la API responde con:

```http
409 Conflict
```

La hora de término de la reserva se calcula automáticamente utilizando la duración del servicio.

### Cancelación

Las reservas no se eliminan físicamente.

Al cancelar una reserva:

```text
ACTIVE → CANCELLED
```

Esto permite conservar el registro de la reserva.

### Borrado lógico

Profesionales, servicios, disponibilidades y relaciones profesional-servicio utilizan un estado:

```text
active = true
active = false
```

Los registros desactivados no aparecen en las consultas normales.

---

## 🔌 Endpoints

### Usuarios

| Método | Endpoint      | Descripción            |
| ------ | ------------- | ---------------------- |
| GET    | `/users`      | Listar usuarios        |
| GET    | `/users/{id}` | Obtener usuario por ID |

### Profesionales

| Método | Endpoint              | Descripción                  |
| ------ | --------------------- | ---------------------------- |
| GET    | `/professionals`      | Listar profesionales activos |
| GET    | `/professionals/{id}` | Obtener profesional          |
| POST   | `/professionals`      | Crear profesional            |
| PUT    | `/professionals/{id}` | Modificar profesional        |
| DELETE | `/professionals/{id}` | Desactivar profesional       |

### Disponibilidad

| Método | Endpoint                                       | Descripción               |
| ------ | ---------------------------------------------- | ------------------------- |
| GET    | `/professionals/{professionalId}/availability` | Listar disponibilidades   |
| POST   | `/professionals/{professionalId}/availability` | Crear disponibilidad      |
| PUT    | `/availability/{id}`                           | Modificar disponibilidad  |
| DELETE | `/availability/{id}`                           | Desactivar disponibilidad |

### Servicios

| Método | Endpoint         | Descripción              |
| ------ | ---------------- | ------------------------ |
| GET    | `/services`      | Listar servicios activos |
| GET    | `/services/{id}` | Obtener servicio         |
| POST   | `/services`      | Crear servicio           |
| PUT    | `/services/{id}` | Modificar servicio       |
| DELETE | `/services/{id}` | Desactivar servicio      |

### Relación profesional-servicio

| Método | Endpoint                                                 | Descripción                      |
| ------ | -------------------------------------------------------- | -------------------------------- |
| POST   | `/professionals/{professionalId}/services/{provisionId}` | Asociar servicio                 |
| DELETE | `/professionals/{professionalId}/services/{provisionId}` | Desactivar asociación            |
| GET    | `/professionals/{id}/services`                           | Listar servicios del profesional |

### Reservas

| Método | Endpoint                                              | Descripción                    |
| ------ | ----------------------------------------------------- | ------------------------------ |
| GET    | `/booking/available?serviceId=&professionalId=&date=` | Consultar turnos disponibles   |
| GET    | `/booking/{userId}`                                   | Obtener reservas de un usuario |
| POST   | `/booking`                                            | Crear reserva                  |
| DELETE | `/booking/{id}`                                       | Cancelar reserva               |

La fecha utilizada en la consulta de disponibilidad debe utilizar el formato:

```text
yyyy-MM-dd
```

---

## 📋 Ejemplo de creación de reserva

### Request

```http
POST /booking
Content-Type: application/json
```

```json
{
  "userId": 1,
  "professionalId": 2,
  "provisionId": 3,
  "date": "2026-09-25",
  "startTime": "10:00"
}
```

La hora de término se determina automáticamente según la duración configurada para el servicio.

---

## ⚠️ Manejo de errores

Las excepciones se gestionan centralizadamente mediante `GlobalExceptionHandler`.

Las respuestas utilizan un formato consistente:

```json
{
  "error": "Mensaje descriptivo del error"
}
```

Principales códigos utilizados:

| Excepción                                 | HTTP |
| ----------------------------------------- | ---: |
| `NotFoundException`                       |  404 |
| `ValidationException`                     |  400 |
| `ConflictException`                       |  409 |
| `DataIntegrityViolationException`         |  400 |
| `IllegalArgumentException`                |  400 |
| `MissingServletRequestParameterException` |  400 |

---

## 🔐 Seguridad

La aplicación utiliza **Spring Security** y está configurada para trabajar bajo un esquema de sesiones:

* Política `STATELESS`.
* CSRF deshabilitado.
* Endpoints de usuarios con acceso público.
* Resto de endpoints protegidos mediante autenticación.
* `AuthenticationService` implementa `UserDetailsService`.
* Los usuarios son cargados mediante su correo electrónico.

La dependencia `JJWT` se encuentra incorporada como preparación para una futura implementación de autenticación mediante tokens JWT.

> **Nota:** actualmente el proyecto no implementa todavía la generación ni validación de tokens JWT.

---

## 🐳 Base de datos con Docker

El proyecto incluye `docker-compose.yml` para levantar una instancia de **MySQL 8.0**.

Desde la carpeta del proyecto:

```bash
docker compose up -d
```

La base de datos utiliza un volumen persistente para conservar la información entre reinicios del contenedor.

---

## ⚙️ Configuración

Se utilizan variables de entorno para configurar la conexión con MySQL:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

El proyecto permite cargar estas variables mediante un archivo `.env`.

Ejemplo:

```env
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/reservas
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=tu_password
```

> No subir credenciales reales al repositorio.

---

## ▶️ Ejecución

### Requisitos

* JDK 21
* Maven
* Docker (opcional)

### 1. Levantar MySQL

```bash
docker compose up -d
```

### 2. Ejecutar la aplicación

Linux / macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

La aplicación se ejecuta por defecto en:

```text
http://localhost:8080
```

---

## 🧪 Pruebas

El proyecto incluye pruebas de contexto de Spring Boot.

Para ejecutarlas:

```bash
./mvnw test
```

En Windows:

```bash
mvnw.cmd test
```

---

## 🔮 Mejoras futuras

Algunas mejoras consideradas para futuras versiones:

* Implementación completa de autenticación mediante JWT.
* Autorización basada en roles.
* Documentación interactiva mediante OpenAPI/Swagger.
* Mayor cobertura de pruebas unitarias e integración.
* Migraciones de base de datos mediante Flyway.
* Validaciones adicionales para fechas y horarios.
* Endpoint de administración para gestionar usuarios y roles.

---

## 👨‍💻 Autor

**Sebastian Diaz**

Ingeniero Civil en Informática.
