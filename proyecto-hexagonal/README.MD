
# Employee Management API
> Arquitectura Hexagonal | Spring Boot 3.4.5 | H2 Database | Swagger | MapStruct

---

## **Descripción**
Employee Management API es un servicio RESTful diseñado bajo principios de **Arquitectura Hexagonal**, utilizando Spring Boot y persistencia en H2 para facilitar la gestión de empleados. Permite realizar operaciones CRUD (Create, Read, Update, Delete) de manera escalable y mantenible.

---

## **Estructura del Proyecto**
```
src/
└── main/
    ├── java/mx/example/proyecto_hexagonal
    │   ├── application/
    │   │   ├── dto/                    # Data Transfer Objects (DTO)
    │   │   │   ├── EmployeeRequest.java
    │   │   │   └── EmployeeResponse.java
    │   │   ├── exceptions/             # Excepciones Personalizadas
    │   │   │   ├── EmployeeNotFoundException.java
    │   │   │   ├── EmployeeAlreadyExistsException.java
    │   │   │   ├── DatabaseConnectionException.java
    │   │   │   ├── HttpRequestFailedException.java
    │   │   │   └── GlobalExceptionHandler.java
    │   │   ├── mapper/                 # Mappers de Entidad ↔ DTO
    │   │   │   └── EmployeeMapper.java
    │   │   └── port/                   # Puertos de Comunicación
    │   │       └── EmployeeServicePort.java
    │   ├── domain/
    │   │   └── model/                  # Modelos de Dominio
    │   │       └── Employee.java
    │   └── infrastructure/
    │       ├── adapter/
    │       │   ├── api/                # Controladores REST
    │       │   │   └── EmployeeController.java
    │       │   └── persistence/        # Adaptador de Persistencia
    │       │       ├── EmployeeRepository.java
    │       │       └── EmployeeServiceImpl.java
    └── resources/
        ├── application.properties      # Configuración de Spring Boot
        └── data.sql                    # Script de SQL para inicialización de datos
```

---

## **Swagger UI**
Para probar los endpoints de manera interactiva y explorar la documentación de la API, accede a Swagger UI en la siguiente URL:

[Swagger UI - Employee Management API](http://localhost:8080/swagger-ui/index.html)


---

## **Colección de Postman**
Para realizar pruebas directas, haz uso de la siguiente coleccion de postman:
[Postman Collection](./Employee_Management_API.postman_collection.json)




## **Endpoints Disponibles**
| Método | Endpoint                  | Descripción                      |
|---------|---------------------------|----------------------------------|
| `GET`   | `/api/v1/employees`      | Obtener la lista de empleados    |
| `GET`   | `/api/v1/employees/{id}` | Obtener un empleado por ID       |
| `POST`  | `/api/v1/employees`      | Crear uno o varios empleados     |
| `PUT`   | `/api/v1/employees/{id}` | Actualizar un empleado por ID    |
| `DELETE`| `/api/v1/employees/{id}` | Eliminar un empleado por ID      |
| `GET`   | `/api/v1/employees/position/{position}` | Obtener empleados por posición |

---

## ⚙️ **Tecnologías Utilizadas**
- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- H2 Database (en memoria)
- Swagger UI
- MapStruct
- Lombok

---

## **Instalación y Ejecución**

1. Compilar el proyecto:
    ```bash
    mvn clean install
    ```

2. Ejecutar la aplicación:
    ```bash
    mvn spring-boot:run
    ```

3. Acceder a Swagger para probar los endpoints:
    - [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## **Pruebas Unitarias**
Para ejecutar las pruebas:
```bash
mvn test
```

---

## **Autor**
- Fidel Francisco Corrales Andrade
- Arquitectura Hexagonal con Spring Boot

---
