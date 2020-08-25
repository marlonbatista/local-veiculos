import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistroAluguelComponent } from './registro-aluguel.component';

describe('RegistroAluguelComponent', () => {
  let component: RegistroAluguelComponent;
  let fixture: ComponentFixture<RegistroAluguelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistroAluguelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistroAluguelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
