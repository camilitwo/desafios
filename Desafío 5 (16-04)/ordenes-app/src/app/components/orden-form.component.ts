console.log('🟢 OrdenFormComponent CARGADO');
import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { OrdenService } from '../services/orden.service';

interface Orden {
  cliente: string;
  fecha: string;
  total: number;
}

@Component({
  selector: 'app-orden-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatSnackBarModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './orden-form.component.html',
  styleUrls: ['./orden-form.component.scss']
})
export class OrdenFormComponent {
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);
  private ordenService = inject(OrdenService);

  isSubmitting = false;

  form: FormGroup = this.fb.group({
    cliente: ['', [
      Validators.required,
      Validators.minLength(2),
      Validators.maxLength(100),
      Validators.pattern(/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/)
    ]],
    fecha: ['', Validators.required]
  });


  // Getters para acceder a los controles del formulario
  get clienteControl() {
    return this.form.get('cliente');
  }

  get fechaControl() {
    return this.form.get('fecha');
  }

  get totalControl() {
    return this.form.get('total');
  }

  submit(): void {
    if (this.form.invalid) {
      this.marcarControlesComoTocados();
      this.mostrarPrimerError();
      return;
    }

    this.isSubmitting = true;

    const orden = {
      cliente: this.clienteControl?.value.trim(),
      fecha: this.fechaControl?.value
    };

    this.ordenService.crearOrden(orden).subscribe({
      next: (ordenCreada) => {
        const agregar = confirm('¿Deseas agregar productos a esta orden?');
        this.mostrarExito('Orden creada exitosamente');

        if (agregar) {
          this.router.navigate([`/ordenes/${ordenCreada.id}/productos`]);
        } else {
          this.router.navigate(['/ordenes']);
        }
      },
      error: (error) => {
        console.error('Error al crear orden:', error);
        this.mostrarError('Error al crear la orden');
      },
      complete: () => {
        this.isSubmitting = false;
      }
    });
  }



  cancelar(): void {
    if (this.form.dirty) {
      const confirmar = confirm('¿Estás seguro de cancelar? Los cambios no guardados se perderán.');
      if (confirmar) {
        this.router.navigate(['/ordenes']);
      }
    } else {
      this.router.navigate(['/ordenes']);
    }
  }

  private marcarControlesComoTocados(): void {
    Object.values(this.form.controls).forEach(control => {
      control.markAsTouched();
    });
  }

  private mostrarPrimerError(): void {
    const controles = ['cliente', 'fecha', 'total'];

    for (const controlName of controles) {
      const control = this.form.get(controlName);
      if (control?.errors && control.touched) {
        const mensaje = this.obtenerMensajeError(controlName, control.errors);
        this.snackBar.open(`Error: ${mensaje}`, 'Cerrar', { duration: 5000 });
        break;
      }
    }
  }

  private obtenerMensajeError(controlName: string, errors: any): string {
    if (errors['required']) {
      return 'Este campo es obligatorio';
    }
    if (errors['minlength']) {
      return `Mínimo ${errors['minlength'].requiredLength} caracteres`;
    }
    if (errors['min']) {
      return `El valor mínimo permitido es ${errors['min'].min}`;
    }
    if (errors['pattern']) {
      return controlName === 'total' ? 'Formato inválido (ej: 25.99)' : 'Solo letras y espacios permitidos';
    }
    return 'Error de validación';
  }

  private mostrarExito(mensaje: string): void {
    this.snackBar.open(`✅ ${mensaje}`, 'Cerrar', {
      duration: 3000,
      panelClass: ['snackbar-success']
    });
  }

  private mostrarError(mensaje: string): void {
    this.snackBar.open(`❌ ${mensaje}`, 'Cerrar', {
      duration: 5000,
      panelClass: ['snackbar-error']
    });
  }
}
