import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { OrdenService } from '../services/orden.service';
import { HttpClientModule } from '@angular/common/http';
import {MatIcon} from '@angular/material/icon';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-orden-producto-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatSnackBarModule,
    HttpClientModule,
    MatIcon
  ],
  templateUrl: './orden-producto-form.component.html',
  styleUrls: ['./orden-producto-form.component.scss']
})
export class OrdenProductoFormComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);
  private readonly snackBar = inject(MatSnackBar);
  private readonly ordenService = inject(OrdenService);

  ordenId!: number;
  form: FormGroup = this.fb.group({
    nombre: ['', Validators.required],
    cantidad: [1, [Validators.required, Validators.min(1)]],
    precioUnitario: [0, [Validators.required, Validators.min(0.01)]]
  });

  ngOnInit(): void {
    this.ordenId = Number(this.route.snapshot.paramMap.get('id'));
    console.log('🟢 ordenId recibido:', this.ordenId, typeof this.ordenId);
  }

  agregarProducto(): void {
    if (this.form.invalid) {
      this.snackBar.open('Completa todos los campos correctamente', 'Cerrar', { duration: 3000 });
      return;
    }

    const producto = this.form.value;
    console.log('📦 Enviando producto:', producto);
    console.log('🧾 A orden ID:', this.ordenId);

    this.ordenService.agregarProductoAOrden(this.ordenId, producto).subscribe({
      next: () => {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Producto agregado exitosamente",
          showConfirmButton: false,
          timer: 3500
        });
        this.form.reset({ cantidad: 1, precioUnitario: 0 });
      },
      error: (error) => {
        console.error('🚨 Error al agregar producto:', error);
        this.snackBar.open('Error al agregar el producto', 'Cerrar', { duration: 3000 });
      }
    });
  }


  volver(): void {
    this.router.navigate(['/ordenes']);
  }
}
