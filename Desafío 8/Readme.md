# Desafío Semana 8: Resolución de Conflictos en Git

## Objetivo

Simular y resolver conflictos reales en un proyecto utilizando Git. El objetivo es aprender a:

* Trabajar en paralelo en diferentes ramas.
* Gestionar conflictos al hacer `merge`.
* Consolidar funcionalidades de diferentes ramas.

---

## Escenario

Tenemos un servicio de órdenes que puede exportar datos:

* Rama `feature/export-csv-improved`: mejora la exportación a CSV.
* Rama `feature/export-excel`: agrega la exportación a Excel.

**Ambas ramas modifican las mismas clases:**

* `OrdenService.java`
* `OrdenController.java`

Esto generará un conflicto que debes resolver.

---

## Base inicial del proyecto

### `OrdenService.java`

```java
@Service
public class OrdenService {
    public List<Orden> listarOrdenes() {
        // Lógica original
        return new ArrayList<>();
    }
}
```

### `OrdenController.java`

```java
@RestController
@RequestMapping("/ordenes")
public class OrdenController {
    @Autowired
    private OrdenService ordenService;

    @GetMapping
    public ResponseEntity<List<Orden>> obtenerOrdenes() {
        List<Orden> ordenes = ordenService.listarOrdenes();
        return ResponseEntity.ok(ordenes);
    }
}
```

---

## Instrucciones para Simular el Conflicto

### 1. Trabajar en `feature/export-csv-improved`

```bash
git checkout main
git pull
git checkout -b feature/export-csv-improved
```

**Cambios en:**

* `OrdenService`: log "Generando CSV de órdenes..."
* `OrdenController`: log "Exportando órdenes a CSV..."

Hacer `commit` y `push`.

### 2. Trabajar en `feature/export-excel`

```bash
git checkout main
git checkout -b feature/export-excel
```

**Cambios en:**

* `OrdenService`: log "Generando archivo Excel de órdenes..."
* `OrdenController`: log "Descargando archivo Excel de órdenes..."

Hacer `commit` y `push`.

### 3. Mergers

1. Mergear primero `feature/export-csv-improved` en `main`.

```bash
git checkout main
git merge feature/export-csv-improved
git push
```

2. Intentar mergear `feature/export-excel` en `main`:

```bash
git merge feature/export-excel
```

**Se generarán conflictos.**

---

## Resolución esperada del conflicto

### En `OrdenService.java`

Resolver para soportar ambos tipos de exportación:

```java
public List<Orden> listarOrdenes(String tipoExportacion) {
    if ("EXCEL".equalsIgnoreCase(tipoExportacion)) {
        System.out.println("Generando archivo Excel de órdenes...");
    } else {
        System.out.println("Generando CSV de órdenes...");
    }
    return new ArrayList<>();
}
```

### En `OrdenController.java`

Modificar el endpoint para recibir el tipo de exportación:

```java
@GetMapping
public ResponseEntity<List<Orden>> obtenerOrdenes(@RequestParam(defaultValue = "CSV") String tipo) {
    List<Orden> ordenes = ordenService.listarOrdenes(tipo);
    if ("EXCEL".equalsIgnoreCase(tipo)) {
        System.out.println("Descargando archivo Excel de órdenes...");
    } else {
        System.out.println("Exportando órdenes a CSV...");
    }
    return ResponseEntity.ok(ordenes);
}
```

---

## Buenas Prácticas

* Leer el conflicto, entender cada cambio antes de resolver.
* No eliminar cambios por accidente.
* Testear el proyecto después de resolver.
* Crear commits claros y descriptivos al finalizar el merge.

---

## Resultado Esperado

* Rama `main` consolidada con soporte a **exportación CSV y Excel**.
* Historial de Git claro.
* Conflictos correctamente resueltos.

---
