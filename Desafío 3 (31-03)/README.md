# Desafío Semana 3: Separación de Responsabilidades con Spring Boot + PostgreSQL

## Objetivo
Aplicar el principio de **Separación de Responsabilidades (SRP)** dividiendo capas correctamente en un mini sistema de gestión de productos, incorporando una estructura limpia con servicios, controladores, entidades, repositorios y DTOs.

## Requisitos
- Java 17
- Spring Boot 3+
- Maven
- PostgreSQL
- Postman o cURL

## Descripción del Desafío

Construir una API REST que permita:
- Crear productos
- Listar productos
- Buscar productos por nombre o categoría

### Entidad `Producto`:
```java
- id (Long)
- nombre (String)
- precio (Double)
- categoría (String)
```

La arquitectura debe dividirse al menos en:
- `Controller`: expone la API.
- `Service`: contiene la lógica de negocio.
- `Repository`: acceso a datos con Spring Data JPA.
- `DTO`: para entrada y salida de datos.

---

## Pasos para completar el desafío

### 1. Crear el proyecto Spring Boot con las dependencias:
- Spring Web
- Spring Data JPA
- PostgreSQL Driver
- Lombok (opcional)

### 2. Configurar `application.yml`
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/tienda
    username: postgres
    password: tu_clave
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 3. Crear la entidad `Producto`
```java
@Entity
public class Producto {
    @Id @GeneratedValue
    private Long id;
    private String nombre;
    private Double precio;
    private String categoria;
    // Getters y Setters
}
```

### 4. Crear DTOs
#### ProductoRequest.java
```java
public class ProductoRequest {
    private String nombre;
    private Double precio;
    private String categoria;
}
```

#### ProductoResponse.java
```java
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String categoria;
    // Constructor, Getters
}
```

### 5. Crear el repositorio
```java
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByCategoria(String categoria);
}
```

### 6. Crear el servicio
```java
@Service
public class ProductoService {
    @Autowired
    private ProductoRepository repository;

    public ProductoResponse crear(ProductoRequest request) {
        Producto p = new Producto();
        p.setNombre(request.getNombre());
        p.setPrecio(request.getPrecio());
        p.setCategoria(request.getCategoria());
        Producto guardado = repository.save(p);
        return new ProductoResponse(guardado.getId(), guardado.getNombre(), guardado.getCategoria());
    }

    public List<ProductoResponse> listar() {
        return repository.findAll().stream()
            .map(p -> new ProductoResponse(p.getId(), p.getNombre(), p.getCategoria()))
            .toList();
    }

    public List<ProductoResponse> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre).stream()
            .map(p -> new ProductoResponse(p.getId(), p.getNombre(), p.getCategoria()))
            .toList();
    }

    public List<ProductoResponse> buscarPorCategoria(String categoria) {
        return repository.findByCategoria(categoria).stream()
            .map(p -> new ProductoResponse(p.getId(), p.getNombre(), p.getCategoria()))
            .toList();
    }
}
```

### 7. Crear el controlador
```java
@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService service;

    @PostMapping
    public ProductoResponse crear(@RequestBody ProductoRequest request) {
        return service.crear(request);
    }

    @GetMapping
    public List<ProductoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/buscar")
    public List<ProductoResponse> buscar(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) String categoria
    ) {
        if (nombre != null) return service.buscarPorNombre(nombre);
        if (categoria != null) return service.buscarPorCategoria(categoria);
        return service.listar();
    }
}
```

---

## Pruebas sugeridas con cURL
```bash
curl -X POST http://localhost:8080/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Laptop", "precio":1500000, "categoria":"Tecnología"}'

curl http://localhost:8080/productos

curl http://localhost:8080/productos/buscar?nombre=laptop

curl http://localhost:8080/productos/buscar?categoria=Tecnología
```

---

## Entrega Esperada
- Repositorio en GitHub con estructura en capas.
- README explicativo con endpoints.
- Base de datos PostgreSQL local funcionando.

## Bonus (Reto Extra)
- Validar los campos de entrada con `@Valid` y `@NotBlank`.
- Implementar control de errores con `@ControllerAdvice`.
- Añadir Swagger para documentar la API.

---

