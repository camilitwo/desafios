# Desafío Semana 6: Exportar y Enviar Órdenes por Email desde Angular

## Objetivo
Extender la SPA de Angular del Desafío 5 para permitir:
- Descargar como archivo CSV las órdenes visibles en la grilla
- Enviar ese archivo CSV a uno o varios correos electrónicos ingresados por el usuario

---

## Requisitos Frontend

### 1. Botón "Descargar CSV"
- Debe estar visible en la vista de la tabla
- Al presionar:
    - Llama al servicio con los filtros actuales
    - Convierte la respuesta a CSV
    - Descarga el archivo usando Blob

#### Ejemplo de función:
```ts
exportToCSV(ordenes: any[]) {
  const headers = ['id', 'cliente', 'fecha', 'total'];
  const rows = ordenes.map(o => `${o.id},${o.cliente},${o.fecha},${o.total}`);
  const csvContent = [headers.join(','), ...rows].join('\n');
  const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
  const link = document.createElement('a');
  link.href = URL.createObjectURL(blob);
  link.download = 'ordenes.csv';
  link.click();
}
```

### 2. Botón "Enviar por Email"
- Abre un **modal** o **componente emergente**
- Permite ingresar múltiples emails dinámicamente
- Al enviar:
    - Llama al backend `POST /ordenes/email`
    - Envia el JSON con ordenes actuales y los emails

#### Ejemplo de estructura del formulario
```ts
form = this.fb.group({
  emails: this.fb.array([
    this.fb.control('', [Validators.required, Validators.email])
  ])
});

addEmail() {
  (this.form.get('emails') as FormArray).push(
    this.fb.control('', [Validators.required, Validators.email])
  );
}
```

#### Llamada al servicio:
```ts
this.ordenService.enviarPorEmail(this.ordenes, this.form.value.emails).subscribe(() => {
  alert('Correo enviado');
});
```

---

## Servicio Angular (`orden.service.ts`)
```ts
enviarPorEmail(ordenes: any[], emails: string[]): Observable<any> {
  return this.http.post(`${this.baseUrl}/email`, { ordenes, emails });
}
```

---

## Requisitos Backend (Spring Boot)

### Endpoint nuevo
```java
@PostMapping("/ordenes/email")
public ResponseEntity<?> enviarPorEmail(@RequestBody EmailRequest request) {
    // 1. Validar emails
    // 2. Generar archivo CSV o Excel
    // 3. Enviar email con JavaMailSender
    return ResponseEntity.ok("Correo enviado");
}
```

### Clase EmailRequest
```java
public class EmailRequest {
    private List<String> emails;
    private List<OrdenResponse> ordenes;
}
```

### Dependencias sugeridas para generar archivos CSV o Excel

#### **Opción 1: Apache POI (Excel)**
##### Maven:
```xml
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.3</version>
</dependency>
```
##### Gradle:
```groovy
dependencies {
    implementation 'org.apache.poi:poi-ooxml:5.2.3'
}
```

#### **Opción 2: OpenCSV (CSV)**
##### Maven:
```xml
<dependency>
    <groupId>com.opencsv</groupId>
    <artifactId>opencsv</artifactId>
    <version>5.8</version>
</dependency>
```
##### Gradle:
```groovy
dependencies {
    implementation 'com.opencsv:opencsv:5.8'
}
```

#### **Opción 3: Java Records + CSV manual (nativo)**
Si no deseas usar librerías externas para CSV:
- Utiliza `StringBuilder` para construir texto CSV manualmente.
- Útil para soluciones simples.

---

## UX Esperada
- Al presionar "Enviar por email", debe mostrarse:
    - Lista de campos para emails con validación
    - Spinner de carga mientras se ejecuta
    - Mensaje de éxito o error

---

## Bonus (Opcionales)
- Mostrar progreso con barra durante el envío



