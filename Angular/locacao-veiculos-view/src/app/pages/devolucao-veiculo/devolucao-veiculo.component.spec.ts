import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DevolucaoVeiculoComponent } from './devolucao-veiculo.component';

describe('DevolucaoVeiculoComponent', () => {
  let component: DevolucaoVeiculoComponent;
  let fixture: ComponentFixture<DevolucaoVeiculoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DevolucaoVeiculoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DevolucaoVeiculoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
