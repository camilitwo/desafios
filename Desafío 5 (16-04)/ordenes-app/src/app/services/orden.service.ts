import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface OrdenResponse {
  id: number;
  cliente: string;
  fecha: string;
  total: number;
}

export interface ProductoResponse {
  nombre: string;
  cantidad: number;
  precioUnitario: number;
}

@Injectable({
  providedIn: 'root'
})
export class OrdenService {
  private readonly apiUrl = 'http://localhost:8080/ordenes';

  constructor(private http: HttpClient) {}

  buscarOrdenes(cliente: string, fecha: string, page: number): Observable<any> {
    const params = new HttpParams()
      .set('cliente', cliente || '')
      .set('fecha', fecha || '')
      .set('page', page.toString());

    return this.http.get<any>(`${this.apiUrl}/buscar`, { params });
  }

  eliminarOrden(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  obtenerProductosPorOrden(idOrden: number): Observable<ProductoResponse[]> {
    return this.http.get<ProductoResponse[]>(`${this.apiUrl}/${idOrden}/productos`);
  }
  crearOrden(data: {
    cliente: string;
    fecha: string;
    total?: number;
  }): Observable<any> {
    return this.http.post<any>('http://localhost:8080/ordenes', data);
  }

  agregarProductoAOrden(idOrden: number, producto: {
    nombre: string;
    cantidad: number;
    precioUnitario: number;
  }): Observable<any> {
    return this.http.post<any>(
      `http://localhost:8080/ordenes/${idOrden}/productos`,
      producto
    );
  }


}
