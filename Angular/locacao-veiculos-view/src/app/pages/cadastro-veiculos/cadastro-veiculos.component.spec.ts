import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastroVeiculosComponent } from './cadastro-veiculos.component';

describe('CadastroVeiculosComponent', () => {
  let component: CadastroVeiculosComponent;
  let fixture: ComponentFixture<CadastroVeiculosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CadastroVeiculosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastroVeiculosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
