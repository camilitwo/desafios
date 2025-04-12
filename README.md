# API de Órdenes de Compra

Este proyecto consiste en una API RESTful desarrollada con Spring Boot 3.4.4 para gestionar órdenes de compra, incluyendo el registro de productos asociados, validaciones, paginación, búsquedas, y documentación con Swagger.

---

## 🚀 Tecnologías utilizadas

- Java 17
- Spring Boot 3.4.4
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Lombok
- Swagger / OpenAPI (springdoc-openapi-starter-webmvc-ui 2.2.0)
- Maven

---

## 🧱 Modelo de Datos

### Entidad `Orden`
```java
Long id
String cliente
LocalDate fecha
Double total
List<Producto> productos
```

### Entidad `Producto`
```java
Long id
String nombre
Integer cantidad
Double precioUnitario
Orden orden
```

---

## 📦 Endpoints principales

### ✅ Crear una orden
**POST** `/ordenes`

```json
{
  "cliente": "Ana Torres",
  "fecha": "2024-07-15",
  "productos": [
    {
      "nombre": "Notebook",
      "cantidad": 1,
      "precioUnitario": 700.00
    }
  ]
}
```

📌 El campo `total` se calcula automáticamente a partir de los productos.

---

### 🔍 Buscar órdenes por cliente o fecha
**GET** `/ordenes/buscar`

Parámetros opcionales:
- `cliente` → coincidencia exacta
- `fecha` → formato `YYYY-MM-DD`

Incluye paginación con `page` y `size`.

---

### 🔍 Buscar con filtros (POST + paginación)
**POST** `/ordenes/buscarpaginado?page=0&size=5`

```json
{
  "cliente": "Pedro"
}
```

---

### 📄 Listar órdenes paginadas
**GET** `/ordenes?page=0&size=10`

---

## 🛡 Validaciones

- `cliente`: obligatorio (`@NotBlank`)
- `fecha`: no puede ser futura (`@PastOrPresent`)
- `productos`: cada producto debe tener:
    - `nombre`: obligatorio
    - `cantidad`: mínimo 1
    - `precioUnitario`: mínimo 1

---

## 📐 Patrón de diseño aplicado

Se utilizó el **Builder Pattern** para construir objetos `OrdenResponse` desde entidades `Orden`, separando la lógica de construcción en una clase especializada:

```java
OrdenResponseBuilder.fromEntity(orden);
```

Esto mejora la organización, reduce repetición y sigue buenas prácticas de diseño orientado a objetos.

---

## 🧪 Documentación interactiva (Swagger)

Disponible en:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🐘 Base de datos

Se utiliza PostgreSQL con conexión por Docker. Tablas creadas:

- `orden`
- `producto`

---

## 📂 Estructura de paquetes

```
com.desafios.sistordenes
├── builder
│   └── OrdenResponseBuilder.java
├── controller
│   └── OrdenController.java
├── dto
│   └── OrdenRequest / OrdenResponse / ProductoRequest / ProductoResponse / OrdenFiltroRequest
├── exception
│   └── GlobalExceptionHandler.java
├── model
│   └── Orden.java / Producto.java
├── repository
│   └── OrdenRepository.java
├── service
│   └── OrdenService.java
└── SistOrdenesApplication.java
```

---

## ✅ Estado

✔ 100% funcional  
✔ Probado con Postman y cURL  
✔ Documentado con Swagger  
✔ Patrón de diseño aplicado  
✔ Validación robusta

---

## 🙌 Autor

Desarrollado por Jorge Gangale como parte del Desafío Semana 4 🚀
