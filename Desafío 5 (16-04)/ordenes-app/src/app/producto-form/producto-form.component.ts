import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { HttpClientModule } from '@angular/common/http';

// Angular Material Modules
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';

import { ProductoService } from '../services/producto.service';

interface ProductoModel {
  nombre: string;
  precioUnitario: number;
  cantidad: number;
}

@Component({
  selector: 'app-producto-form',
  standalone: true,
  templateUrl: './producto-form.component.html',
  styleUrls: ['./producto-form.component.scss'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    HttpClientModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule,
    MatIconModule,
    MatCardModule
  ]
})
export class ProductoFormComponent {
  private readonly fb = inject(FormBuilder);
  private readonly router = inject(Router);
  private readonly snackBar = inject(MatSnackBar);
  private readonly productoService = inject(ProductoService);

  readonly form: FormGroup = this.fb.group({
    nombre: ['', [
      Validators.required,
      Validators.minLength(2),
      Validators.maxLength(50),
      Validators.pattern(/^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\s]+$/)
    ]],
    precioUnitario: [null, [
      Validators.required,
      Validators.min(0.01),
      Validators.max(999999.99)
    ]],
    cantidad: [null, [
      Validators.required,
      Validators.min(1),
      Validators.max(9999)
    ]]
  });

  get nombreControl(): FormControl {
    return this.form.get('nombre') as FormControl;
  }

  get precioControl(): FormControl {
    return this.form.get('precioUnitario') as FormControl;
  }

  get cantidadControl(): FormControl {
    return this.form.get('cantidad') as FormControl;
  }

  submit(): void {
    if (this.form.invalid) {
      this.markAllTouched();
      this.showFirstError();
      return;
    }

    const nuevoProducto: ProductoModel = {
      nombre: this.nombreControl.value.trim(),
      precioUnitario: this.precioControl.value,
      cantidad: this.cantidadControl.value
    };

    this.productoService.crearProducto(nuevoProducto).subscribe({
      next: () => {
        this.snackBar.open('✅ Producto creado con éxito', 'Cerrar', {
          duration: 3000,
          panelClass: ['success-snackbar']
        });
        this.router.navigate(['/productos']);
      },
      error: (err) => {
        console.error('Error al crear producto:', err);
        this.snackBar.open('❌ Error al registrar el producto', 'Cerrar', {
          duration: 3000,
          panelClass: ['error-snackbar']
        });
      }
    });
  }

  cancelar(): void {
    this.router.navigate(['/productos']);
  }

  private markAllTouched(): void {
    Object.values(this.form.controls).forEach(control => {
      control.markAsTouched();
    });
  }

  private showFirstError(): void {
    const controlName = Object.keys(this.form.controls).find(
      key => this.form.get(key)?.invalid
    );
    if (controlName) {
      this.snackBar.open(`⚠️ Revisa el campo "${controlName}"`, 'Entendido', {
        duration: 3000
      });
    }
  }
}
