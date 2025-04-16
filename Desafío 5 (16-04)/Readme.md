# Desafío Semana 5: Frontend Angular para Órdenes de Compra

## Objetivo
Construir una SPA (Single Page Application) en Angular para consumir la API de órdenes del Desafío 4. Debes poder visualizar, registrar, buscar y eliminar órdenes.

## Tecnologías Requeridas
- Angular 18+ (standalone components, sin `AppModule`)
- TypeScript
- Angular CLI
- Angular Router
- Angular Reactive Forms
- HttpClient
- RxJS
- Angular Material (opcional pero recomendado para UI)

---

## Estructura de la Aplicación
```
src/app/
├── components/
│   ├── orden-list.component.ts
│   └── orden-form.component.ts
├── services/
│   └── orden.service.ts
├── pages/
│   ├── home.page.ts
│   └── crear.page.ts
├── app.routes.ts
└── main.ts
```

---

## Instalación Inicial
```bash
ng new ordenes-app --standalone
cd ordenes-app
npm install
```

---

## 1. Crear Servicio HTTP (`orden.service.ts`)
```ts
@Injectable({ providedIn: 'root' })
export class OrdenService {
  private baseUrl = 'http://localhost:8080/ordenes';

  constructor(private http: HttpClient) {}

  getOrdenes(page = 0, size = 10): Observable<any> {
    return this.http.get(`${this.baseUrl}?page=${page}&size=${size}`);
  }

  buscarOrdenes(cliente?: string, fecha?: string, page = 0, size = 10): Observable<any> {
    let query = [];
    if (cliente) query.push(`cliente=${cliente}`);
    if (fecha) query.push(`fecha=${fecha}`);
    query.push(`page=${page}&size=${size}`);
    return this.http.get(`${this.baseUrl}/buscar?${query.join('&')}`);
  }

  crearOrden(orden: any): Observable<any> {
    return this.http.post(this.baseUrl, orden);
  }

  eliminarOrden(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`); // si se implementa
  }
}
```

---

## 2. Crear Listado de Órdenes (`orden-list.component.ts`)
- Usar tabla con paginación (Angular Material `mat-table` o HTML puro)
- Filtros por cliente/fecha
- Botones: crear, eliminar

```ts
@Component({
  selector: 'app-orden-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './orden-list.component.html'
})
export class OrdenListComponent implements OnInit {
  ordenes = [];
  totalPages = 0;
  page = 0;

  cliente = '';
  fecha = '';

  constructor(private ordenService: OrdenService) {}

  ngOnInit() {
    this.cargarOrdenes();
  }

  cargarOrdenes() {
    this.ordenService.buscarOrdenes(this.cliente, this.fecha, this.page)
      .subscribe(data => {
        this.ordenes = data.content;
        this.totalPages = data.totalPages;
      });
  }

  buscar() {
    this.page = 0;
    this.cargarOrdenes();
  }

  cambiarPagina(p: number) {
    this.page = p;
    this.cargarOrdenes();
  }
}
```

---

## 3. Crear Formulario de Registro (`orden-form.component.ts`)
```ts
@Component({
  selector: 'app-orden-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './orden-form.component.html'
})
export class OrdenFormComponent {
  form = this.fb.group({
    cliente: ['', Validators.required],
    fecha: ['', Validators.required],
    total: [0, [Validators.required, Validators.min(1)]]
  });

  constructor(private fb: FormBuilder, private service: OrdenService, private router: Router) {}

  submit() {
    if (this.form.valid) {
      this.service.crearOrden(this.form.value).subscribe(() => {
        alert('Orden registrada');
        this.router.navigate(['/']);
      });
    }
  }
}
```

---

## 4. Definir Rutas (`app.routes.ts`)
```ts
export const routes: Routes = [
  { path: '', component: OrdenListComponent },
  { path: 'crear', component: OrdenFormComponent }
];
```

En `main.ts`:
```ts
bootstrapApplication(RootComponent, {
  providers: [provideRouter(routes), importProvidersFrom(HttpClientModule)]
});
```

---

## 5. Validaciones y UX
- Mostrar errores de formulario con `form.get('campo')?.invalid`
- Mostrar mensajes de éxito o error con `MatSnackBar` o `alert()`
- Agregar `Loading` si es posible

---

## Bonus
- Confirmación al eliminar
- Tabla responsive para móvil
- Tema claro/oscuro con Angular Material
- Componente reutilizable para formulario o tabla

---

