import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { NgxSpinnerModule } from 'ngx-spinner';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CardComponent } from './components/card/card.component';
import { CadastroClienteComponent } from './pages/cadastro-cliente/cadastro-cliente.component';
import { CadastroVeiculosComponent } from './pages/cadastro-veiculos/cadastro-veiculos.component';
import { DevolucaoVeiculoComponent } from './pages/devolucao-veiculo/devolucao-veiculo.component';
import { HomeComponent } from './pages/home/home.component';
import { RegistroAluguelComponent } from './pages/registro-aluguel/registro-aluguel.component';
import { RelatorioComponent } from './pages/relatorio/relatorio.component';
import { HttpClientModule } from '@angular/common/http';

import {
  MatToolbarModule, MatIconModule,
  MatButtonModule, MatSidenavModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatListModule,
  MatTableModule,
  MatCardModule,
  MatGridListModule,
  MatFormField,
  MatInputModule,
  MatSnackBarModule,
  MatPaginatorModule,
  MatPaginatorIntl,
  MatSelectModule,
  MatRadioModule,
  MatCheckboxModule
} from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AppComponent,
    CardComponent,
    HomeComponent,
    CadastroClienteComponent,
    CadastroVeiculosComponent,
    RegistroAluguelComponent,
    RegistroAluguelComponent,
    DevolucaoVeiculoComponent,
    RelatorioComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserAnimationsModule,
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCardModule,
    MatCheckboxModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatPaginatorModule,
    MatSelectModule,
    MatSidenavModule,
    MatSnackBarModule,
    MatRadioModule,
    MatTableModule,
    MatToolbarModule,
    NgxSpinnerModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
