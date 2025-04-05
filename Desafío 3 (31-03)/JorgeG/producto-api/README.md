# 🛍️ API de Gestión de Productos

API RESTful para administrar productos desarrollada con Spring Boot y PostgreSQL.

## 📋 Endpoints

| Método | Endpoint                     | Descripción                          |
|--------|------------------------------|--------------------------------------|
| POST   | `/api/productos`              | Crear un nuevo producto              |
| GET    | `/api/productos`              | Obtener todos los productos          |
| GET    | `/api/productos/buscar`       | Buscar productos por nombre/categoría|

## 📝 Ejemplos de Uso

### Crear un producto
```bash
curl -X POST 'http://localhost:8080/api/productos' \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Smartphone",
    "precio": 899.99,
    "categoria": "Electrónicos"
  }'

##Listar todos los productos
curl 'http://localhost:8080/api/productos'
##Buscar productos
# Por nombre
curl 'http://localhost:8080/api/productos/buscar?nombre=smart'

# Por categoría
curl 'http://localhost:8080/api/productos/buscar?categoria=Electrónicos'

⚙️ Configuración

Requisitos:
Java 17+
PostgreSQL 14+
Maven 3.8+
##Variables de entorno:
spring.datasource.url=jdbc:postgresql://localhost:5432/tienda
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña

##Ejecución:
mvn spring-boot:run

##🛠️ Estructura del Proyecto
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── ejemplo/
│   │           └── productosapi/
│   │               ├── controller/
│   │               ├── service/
│   │               ├── repository/
│   │               ├── entity/
│   │               └── dto/
│   └── resources/
│       └── application.properties

##📄 Licencia
MIT © 2025 @Darkmork


Este archivo README.md incluye:
- Encabezado claro con emoji
- Tabla de endpoints organizada
- Ejemplos de curl con sintaxis resaltada
- Sección de configuración detallada
- Estructura de proyecto visual
- Licencia al final

Puedes copiarlo directamente a tu archivo README.md y personalizar los detalles según necesites.
