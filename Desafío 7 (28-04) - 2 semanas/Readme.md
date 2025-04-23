# Desafío Semana 7: Autenticación y Autorización con Roles en Spring Boot + Angular

## Objetivo
Agregar al sistema Órdenes de Compra **autenticación con JWT** y control de accesos basado en **roles** en el proyecto de órdenes. Se restringirán acciones en el backend y el frontend según si el usuario es `ADMIN` o `USER`.

> **Importante:** A partir de este punto, **todas las APIs construidas desde la semana 4 deben requerir autenticación JWT válida**. El acceso anónimo quedará restringido.

---

## Roles
- `ADMIN`: puede crear, listar, eliminar, enviar por correo
- `USER`: solo puede listar y exportar CSV

---

## Backend - Spring Boot

### 1. Entidad Usuario
```java
@Entity
public class Usuario {
  @Id
  private String email;
  private String password; // encriptada con BCrypt
  private String rol; // "ADMIN" o "USER"
}
```

### 2. Endpoint de Login
```java
@RestController
@RequestMapping("/auth")
public class AuthController {
  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
    // 1. Validar credenciales
    // 2. Generar token JWT con rol
    // 3. Retornar token
  }
}
```

### 3. Estructura de TokenResponse y LoginRequest
```java
public class LoginRequest {
  private String email;
  private String password;
}

public class TokenResponse {
  private String token;
}
```

### 4. Configurar Seguridad
- Usa `SecurityFilterChain` para definir los accesos:
```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  http.csrf().disable()
    .authorizeHttpRequests()
    .requestMatchers("/auth/**").permitAll()
    .requestMatchers(HttpMethod.POST, "/ordenes").hasRole("ADMIN")
    .requestMatchers(HttpMethod.POST, "/ordenes/email").hasRole("ADMIN")
    .anyRequest().authenticated() // todas las demás requieren JWT
    .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  return http.build();
}
```

### 5. JWT - Dependencias
```xml
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-api</artifactId>
  <version>0.11.5</version>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

---

## Frontend - Angular (Standalone)

### 1. Componente Login
- Formulario con email + password
- Envía POST a `/auth/login`
- Guarda JWT en `localStorage`

### 2. AuthService
```ts
@Injectable({ providedIn: 'root' })
export class AuthService {
  getToken(): string | null { return localStorage.getItem('token'); }
  getRole(): string | null {
    const token = this.getToken();
    if (!token) return null;
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.role || null;
  }
  isLoggedIn(): boolean { return !!this.getToken(); }
  logout() { localStorage.removeItem('token'); }
}
```

### 3. Botones Condicionales por Rol
```html
<button *ngIf="auth.getRole() === 'ADMIN'">Crear Orden</button>
```

### 4. Route Guard (opcional)
- Crear un guard que redirija si no es `ADMIN`

### 5. Incluir token en headers
```ts
intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
  const token = this.auth.getToken();
  if (token) {
    req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
  }
  return next.handle(req);
}
```

---

## Extras Obligatorios
- El token debe expirar
- El frontend debe cerrar sesión si el token expira (opcional con expiry check manual)
- El backend debe devolver 401 en caso de acceso prohibido (manejar esto con controllerAdvice)

---

## Bonus
- Registro de usuarios (solo admin)
- Pantalla de perfil
- Refresh token con expiración extendida

---

