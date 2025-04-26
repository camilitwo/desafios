import { Routes } from '@angular/router';
import { OrdenListComponent } from './components/orden-list.component';
import { OrdenFormComponent } from './components/orden-form.component';
import { ProductoListComponent } from './producto-form/producto-list.component';
import { ProductoFormComponent } from './producto-form/producto-form.component';
import {OrdenProductoFormComponent} from './orden-producto-form/orden-producto-form.component';

export const routes: Routes = [
  {
    path: 'ordenes',
    children: [
      { path: '', component: OrdenListComponent }, // /ordenes
      { path: 'crear', component: OrdenFormComponent }, // /ordenes/crear
      { path: ':id/productos', component: OrdenProductoFormComponent } // /ordenes/1/productos
    ]
  },
  { path: 'productos', component: ProductoListComponent },
  { path: 'productos/crear', component: ProductoFormComponent },
  { path: 'productos/editar/:id', component: ProductoFormComponent },
  { path: '', redirectTo: 'ordenes', pathMatch: 'full' },
  { path: '**', redirectTo: 'ordenes' }
];
