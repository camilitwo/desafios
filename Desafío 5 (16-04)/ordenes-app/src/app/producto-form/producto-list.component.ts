import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ProductoService } from '../services/producto.service';
import { ThemeService } from '../services/theme.service';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-producto-list',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatTableModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatCardModule
  ],
  templateUrl: './producto-list.component.html',
  styleUrls: ['./producto-list.component.scss']
})
export class ProductoListComponent implements OnInit {
  productos: any[] = [];
  displayedColumns = ['id', 'nombre', 'precioUnitario', 'cantidad', 'acciones'];

  readonly fb = inject(FormBuilder);
  readonly theme = inject(ThemeService);
  readonly productoService = inject(ProductoService);

  filtroForm: FormGroup = this.fb.group({
    nombre: ['']
  });

  page = 0;
  totalPages = 1;

  ngOnInit(): void {
    this.cargarProductos();
  }

  buscar(): void {
    const { nombre } = this.filtroForm.value;
    this.page = 0;
    this.productoService.buscarProductos(nombre, this.page).subscribe(data => {
      this.productos = data.content ?? [];
      this.totalPages = data.totalPages;
    });
  }

  cargarProductos(): void {
    this.buscar();
  }

  cambiarPagina(delta: number): void {
    const { nombre } = this.filtroForm.value;
    const nuevaPagina = this.page + delta;
    if (nuevaPagina >= 0 && nuevaPagina < this.totalPages) {
      this.page = nuevaPagina;
      this.productoService.buscarProductos(nombre, this.page).subscribe(data => {
        this.productos = data.content ?? [];
        this.totalPages = data.totalPages;
      });
    }
  }

  eliminar(id: number): void {
    if (confirm('¿Estás seguro de eliminar este producto?')) {
      this.productoService.eliminarProducto(id).subscribe(() => this.cargarProductos());
    }
  }
}
