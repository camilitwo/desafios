# Desafío Semana 4: API de Órdenes de Compra con Validación y Filtros

## Objetivo
Crear una API REST para registrar y consultar órdenes de compra, incorporando:
- Uso de DTOs
- Validaciones con `@Valid`
- Consultas filtradas por cliente y fecha
- Manejo simple de errores con `@ControllerAdvice`
- Organización en capas (controller, service, repository)
- **Uso obligatorio de al menos un patrón de diseño**
- **Uso obligatorio de paginación con `Pageable` debido a alta cantidad de registros**

## Tecnologías
- Java 17
- Spring Boot 3+
- Maven
- PostgreSQL
- Spring Data JPA
- Lombok (opcional)

## Modelo de Datos

### Entidad `Orden`
```java
@Entity
public class Orden {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;

    private LocalDate fecha;

    private Double total;
}
```

### DTOs
#### `OrdenRequest`
```java
public class OrdenRequest {
    @NotBlank //ivestigar 
    private String cliente;

    @PastOrPresent //ivestigar 
    private LocalDate fecha;

    @Min(1) //ivestigar 
    private Double total;
}
```

#### `OrdenResponse`
```java
public class OrdenResponse {
    private Long id;
    private String cliente;
    private LocalDate fecha;
    private Double total;
}
```

## Repositorio
```java
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    Page<Orden> findByClienteContainingIgnoreCase(String cliente, Pageable pageable);
    Page<Orden> findByFecha(LocalDate fecha, Pageable pageable);
}
```

## Servicio
```java
@Service
public class OrdenService {
    @Autowired
    private OrdenRepository repository;

    public OrdenResponse crear(OrdenRequest request) {
        Orden orden = new Orden();
        orden.setCliente(request.getCliente());
        orden.setFecha(request.getFecha());
        orden.setTotal(request.getTotal());
        Orden guardada = repository.save(orden);
        return new OrdenResponse(guardada.getId(), guardada.getCliente(), guardada.getFecha(), guardada.getTotal());
    }

    public Page<OrdenResponse> listar(Pageable pageable) {
        return repository.findAll(pageable).map(
            o -> new OrdenResponse(o.getId(), o.getCliente(), o.getFecha(), o.getTotal())
        );
    }

    public Page<OrdenResponse> buscar(String cliente, LocalDate fecha, Pageable pageable) {
        if (cliente != null) {
            return repository.findByClienteContainingIgnoreCase(cliente, pageable)
                .map(o -> new OrdenResponse(o.getId(), o.getCliente(), o.getFecha(), o.getTotal()));
        } else if (fecha != null) {
            return repository.findByFecha(fecha, pageable)
                .map(o -> new OrdenResponse(o.getId(), o.getCliente(), o.getFecha(), o.getTotal()));
        }
        return listar(pageable);
    }
}
```

## Controlador
```java
@RestController
@RequestMapping("/ordenes")
public class OrdenController {
    @Autowired
    private OrdenService service;

    @PostMapping
    public ResponseEntity<OrdenResponse> crear(@RequestBody @Valid OrdenRequest request) {
        return ResponseEntity.ok(service.crear(request));
    }

    @GetMapping
    public ResponseEntity<Page<OrdenResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<OrdenResponse>> buscar(
            @RequestParam(required = false) String cliente,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Pageable pageable) {
        return ResponseEntity.ok(service.buscar(cliente, fecha, pageable));
    }
}
```

## Manejo de errores global con `@ControllerAdvice`
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body("Error de validación: " + msg);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
    }
}
```

## Script de creación del modelo y datos iniciales (PostgreSQL), dependiendo de tu motor de base de datos deberás adaptarlo
```sql
CREATE TABLE orden (
    id SERIAL PRIMARY KEY,
    cliente VARCHAR(255) NOT NULL,
    fecha DATE NOT NULL,
    total NUMERIC(10,2) NOT NULL
);
```

## Genera un script de inserción de datos masivos para probar la paginación
```sql
-- Investigar como hacer un script de inserción masiva
```


## Ejemplos de prueba con cURL
```bash
curl -X POST http://localhost:8080/ordenes \
  -H "Content-Type: application/json" \
  -d '{"cliente":"Pedro", "fecha":"2024-07-10", "total":10000}'

curl http://localhost:8080/ordenes?page=0&size=10

curl http://localhost:8080/ordenes/buscar?cliente=Cliente_1&page=0&size=10

curl http://localhost:8080/ordenes/buscar?fecha=2024-07-01&page=0&size=10
```

---

## Uso obligatorio de patrón de diseño
Se debe aplicar al menos **uno** de los siguientes patrones:
- **Factory Pattern**: por ejemplo, para instanciar tipos de orden según reglas.
- **Strategy Pattern**: para definir diferentes formas de calcular el total según tipo de cliente.
- **Builder Pattern**: para construir objetos DTO u orden con lógicas complejas.

Se deberá documentar en el README qué patrón fue usado y dónde.

---

## Bonus (opcional)
- Agregar Swagger para documentar la API
- Crear una entidad `Producto` relacionada a `Orden` y extender el modelo

---


