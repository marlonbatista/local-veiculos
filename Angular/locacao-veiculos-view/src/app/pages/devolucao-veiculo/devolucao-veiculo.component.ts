import { Component, OnInit } from '@angular/core';
import Aluguel from 'src/app/Interfaces/Aluguel';
import { AluguelService } from 'src/app/services/aluguel.serve';
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import Devolucao from 'src/app/Interfaces/Devolucao';

@Component({
  selector: 'app-devolucao-veiculo',
  templateUrl: './devolucao-veiculo.component.html',
  styleUrls: ['./devolucao-veiculo.component.scss']
})
export class DevolucaoVeiculoComponent implements OnInit {

  constructor(
    private AluguelSrv: AluguelService,
    private matSnack: MatSnackBar,
    private router: Router,
    private spinner: NgxSpinnerService) { }

  model: Devolucao = {
    cod_aluguel: null,
    cod_cliente: null,
    cod_veiculo: null,
    data_devolucao: null,
    data_locacao: null,
    valor_total: null,
    status: false,
    nome_cliente: '',
    nome_veiculo: '',
  };

  ngOnInit() {
  }

  async buscarAluguel(): Promise<void> {
    if (this.model.cod_aluguel === null) {
      this.chamarSnakBar('Informe o código do aluguel');
      return;
    }
    this.spinner.show();
    const result = await this.AluguelSrv.GetDeliveryByIdAluguel('' + this.model.cod_aluguel);
    if (!result.success || !result.data) {
      this.spinner.hide();
      this.chamarSnakBar('Aluguel não localizado');
    } else {
      this.model = result.data;
      console.log(result.data);
      this.spinner.hide();
    }
  }

  async save(): Promise<void> {
    const result = await this.AluguelSrv.deliver(this.model);
    if (result.success) {
      if (this.model.cod_aluguel !== null) {
        this.chamarSnakBar('Aluguel alterado com sucesso');
      } else {
        this.chamarSnakBar('Aluguel salvo com sucesso');
      }
      this.model = result.data;
    }
  }

  async delete(): Promise<void> {
    if (this.model.cod_veiculo !== null) {
      const result = await this.AluguelSrv.delete('' + this.model.cod_veiculo);
      if (result.success) {
        this.limparModel();
        this.matSnack.open('Veículo excluído com sucesso', undefined, { duration: 3000 });
      } else {
        this.matSnack.open('Não foi possível excluir o veiculo', undefined, { duration: 3000 });
      }
    } else {
      this.matSnack.open('Informe um código de veículo válido', undefined, { duration: 3000 });
    }
  }

  limparModel() {
    this.model = {
      cod_aluguel: null,
      cod_cliente: null,
      cod_veiculo: null,
      data_devolucao: null,
      data_locacao: null,
      nome_cliente: '',
      nome_veiculo: '',
      valor_total: 0.00,
      status: true,
      nome_cliente: '',
      nome_veiculo: '',
    };
  }

  chamarSnakBar(message: string): void {
    this.matSnack.open(message, undefined, { duration: 3000 });
  }

}
