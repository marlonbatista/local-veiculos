import { Component, OnInit } from '@angular/core';
import Aluguel from 'src/app/Interfaces/Aluguel';
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { AluguelService } from 'src/app/services/aluguel.serve';

@Component({
  selector: 'app-registro-aluguel',
  templateUrl: './registro-aluguel.component.html',
  styleUrls: ['./registro-aluguel.component.scss']
})
export class RegistroAluguelComponent implements OnInit {

  constructor(
    private AluguelSrv: AluguelService,
    private matSnack: MatSnackBar,
    private router: Router,
    private spinner: NgxSpinnerService) { }

  model: Aluguel = {
    cod_aluguel: null,
    cod_cliente: null,
    cod_veiculo: null,
    data_devolucao: null,
    data_locacao: null,
    valor_total: null,
    status: false,
  };

  ngOnInit() {
  }

  async buscarAluguel(): Promise<void> {
    if (this.model.cod_aluguel === null) {
      this.chamarSnakBar('Informe o código do aluguel');
      return;
    }
    this.spinner.show();
    const result = await this.AluguelSrv.GetById(this.model.cod_aluguel);
    if (!result.success || !result.data) {
      this.spinner.hide();
      this.chamarSnakBar('Aluguel não localizado');
    } else {
      this.model = result.data;
      this.spinner.hide();
    }
  }

  async save(): Promise<void> {
    const result = await this.AluguelSrv.post(this.model);
    if (result.success) {
      if (this.model.cod_aluguel !== null) {
        this.chamarSnakBar('Aluguel alterado com sucesso');
      } else {
        this.chamarSnakBar('Aluguel salvo com sucesso');
      }
      this.model = result.data;
    }
  }

  limparModel() {
    this.model = {
      cod_aluguel: null,
      cod_cliente: null,
      cod_veiculo: null,
      data_devolucao: null,
      data_locacao: null,
      valor_total: 0.00,
      status: true,
    };
  }

  chamarSnakBar(message: string): void {
    this.matSnack.open(message, undefined, { duration: 3000 });
  }

}
