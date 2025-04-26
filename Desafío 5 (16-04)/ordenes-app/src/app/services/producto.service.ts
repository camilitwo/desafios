import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Producto } from '../model/producto.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private baseUrl = 'http://localhost:8080/productos';

  constructor(private http: HttpClient) {}

  crearProducto(producto: Producto): Observable<Producto> {
    return this.http.post<Producto>(this.baseUrl, producto);
  }

  listarProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.baseUrl);
  }

  eliminarProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
  buscarProductos(nombre: string, page: number = 0) {
    const params = new HttpParams()
      .set('nombre', nombre)
      .set('page', page.toString());

    return this.http.get<any>('http://localhost:8080/productos/buscar', { params });
  }

}
