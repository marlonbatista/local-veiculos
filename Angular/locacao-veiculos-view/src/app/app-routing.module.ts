import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { CadastroVeiculosComponent } from './pages/cadastro-veiculos/cadastro-veiculos.component';
import { CadastroClienteComponent } from './pages/cadastro-cliente/cadastro-cliente.component';
import { RegistroAluguelComponent } from './pages/registro-aluguel/registro-aluguel.component';
import { DevolucaoVeiculoComponent } from './pages/devolucao-veiculo/devolucao-veiculo.component';
import { RelatorioComponent } from './pages/relatorio/relatorio.component';


const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/home' },
  { path: 'home', component: HomeComponent },
  { path: 'home/cadastro-veiculo', component: CadastroVeiculosComponent },
  { path: 'home/cadastro-cliente', component: CadastroClienteComponent },
  { path: 'home/registro-aluguel', component: RegistroAluguelComponent },
  { path: 'home/devolucao', component: DevolucaoVeiculoComponent },
  { path: 'home/relatorio', component: RelatorioComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
