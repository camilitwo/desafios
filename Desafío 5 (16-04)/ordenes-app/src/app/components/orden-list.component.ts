import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { OrdenService } from '../services/orden.service';
import { ThemeService } from '../services/theme.service';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { MatCard } from '@angular/material/card';
import {Router, RouterModule} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-orden-list',
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
    MatCard,
    RouterModule
  ],
  templateUrl: './orden-list.component.html',
  styleUrls: ['./orden-list.component.scss']
})
export class OrdenListComponent implements OnInit {
  ordenes: any[] = [];
  productosPorOrden: { [idOrden: number]: any[] } = {};
  displayedColumns = ['id', 'cliente', 'fecha', 'total', 'acciones'];

  readonly fb = inject(FormBuilder);
  readonly theme = inject(ThemeService);
  readonly ordenService = inject(OrdenService);
  readonly router = inject(Router);
  readonly snackBar = inject(MatSnackBar);



  filtroForm: FormGroup = this.fb.group({
    cliente: [''],
    fecha: ['']
  });

  page = 0;
  totalPages = 1;

  ngOnInit(): void {
    this.cargarOrdenes();
  }

  buscar(): void {
    const { cliente, fecha } = this.filtroForm.value;
    this.page = 0;
    this.ordenService.buscarOrdenes(cliente, fecha, this.page).subscribe(data => {
      this.ordenes = data.content ?? [];
      this.totalPages = data.totalPages;
    });
  }

  cargarOrdenes(): void {
    this.buscar();
  }

  cambiarPagina(delta: number): void {
    const { cliente, fecha } = this.filtroForm.value;
    const nuevaPagina = this.page + delta;
    if (nuevaPagina >= 0 && nuevaPagina < this.totalPages) {
      this.page = nuevaPagina;
      this.ordenService.buscarOrdenes(cliente, fecha, this.page).subscribe(data => {
        this.ordenes = data.content ?? [];
        this.totalPages = data.totalPages;
      });
    }
  }

  verProductosDeOrden(ordenId: number): void {
    if (this.productosPorOrden[ordenId]) {
      delete this.productosPorOrden[ordenId]; // ✅ Limpia los datos antiguos
    } else {
      this.ordenService.obtenerProductosPorOrden(ordenId).subscribe(productos => {
        this.productosPorOrden = { [ordenId]: productos }; // ✅ Sobrescribe el objeto
      });
    }
  }
  agregarProducto(id: number): void {
    this.router.navigate([`/ordenes/${id}/productos`]);
  }

  eliminar(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta orden?')) {
      this.ordenService.eliminarOrden(id).subscribe({
        next: () => {
          this.snackBar.open('🗑️ Orden eliminada con éxito', 'Cerrar', { duration: 3000 });
          this.cargarOrdenes(); // Recarga la lista
        },
        error: () => {
          this.snackBar.open('❌ Error al eliminar la orden', 'Cerrar', { duration: 3000 });
        }
      });
    }
  }


}
