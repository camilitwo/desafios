import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdenProductoFormComponent } from './orden-producto-form.component';

describe('OrdenProductoFormComponent', () => {
  let component: OrdenProductoFormComponent;
  let fixture: ComponentFixture<OrdenProductoFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrdenProductoFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrdenProductoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
