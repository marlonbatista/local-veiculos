import { Component, OnInit } from '@angular/core';
import { Veiculo } from 'src/app/Interfaces/Veiculo';
import { EnumCombustivel } from 'src/app/Enums/EnumCombustivel';
import { ClienteService } from 'src/app/services/cliente.service';
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { VeiculoService } from 'src/app/services/veiculo.service';

@Component({
  selector: 'app-cadastro-veiculos',
  templateUrl: './cadastro-veiculos.component.html',
  styleUrls: ['./cadastro-veiculos.component.scss']
})
export class CadastroVeiculosComponent implements OnInit {

  model: Veiculo = {
    ano: null,
    cod_veiculo: null,
    combustivel: EnumCombustivel.Etanol,
    marca: '',
    modelo: null,
    nome: '',
    valor_diaria: null,
    status: true
  };

  constructor(
    private veiculoSrv: VeiculoService,
    private matSnack: MatSnackBar,
    private router: Router,
    private spinner: NgxSpinnerService
  ) { }

  ngOnInit() {
  }

  async buscarVeiculo(): Promise<void> {
    if (this.model.cod_veiculo === null) {
      this.matSnack.open('Informe o código do veículo', undefined, { duration: 3000 });
      return;
    }
    this.spinner.show();
    const result = await this.veiculoSrv.GetById(this.model.cod_veiculo);
    if (!result.success || !result.data) {
      this.spinner.hide();
      this.matSnack.open('Veículo não localizado', undefined, { duration: 3000 });
    } else {
      this.model = result.data;
      this.spinner.hide();
    }
  }

  async save(): Promise<void> {
    if (!this.validarCampos()) {
      return;
    }
    const result = await this.veiculoSrv.post(this.model);
    if (result.success) {
      if (this.model.cod_veiculo !== null) {
        this.matSnack.open('Veículo alterado com sucesso', undefined, { duration: 3000 });
      } else {
        this.matSnack.open('Veículo salvo com sucesso', undefined, { duration: 3000 });
      }
      this.model = result.data;
    }
  }

  async delete(): Promise<void> {
    if (this.model.cod_veiculo !== null) {
      const result = await this.veiculoSrv.delete('' + this.model.cod_veiculo);
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

  limparModel(): void {
    this.model = {
      ano: null,
      cod_veiculo: null,
      combustivel: EnumCombustivel.Etanol,
      marca: '',
      modelo: null,
      nome: '',
      valor_diaria: null,
      status: true
    };
  }

  validarCampos(): boolean {
    if (this.model.ano < 2000 || !this.model.ano) {
      this.chamarSnakBar('O ano do veículo deve ser igual ou maior que 2000');
      return false;
    }
    if (this.model.modelo < this.model.ano) {
      this.chamarSnakBar('O modelo deve ser igual o ano do veículo');
      return false;
    }
    if (!this.model.marca) {
      this.chamarSnakBar('O campo marca não deve ser nulo');
      return false;
    }
    if (this.model.marca.length > 30) {
      this.chamarSnakBar('O tamanho máximo para marca é de 30 caracteres');
      return false;
    }
    if (!this.model.nome) {
      this.chamarSnakBar('O campo nome não deve ser nulo');
      return false;
    }
    if (this.model.nome.length > 100) {
      this.chamarSnakBar('O tamanho máximo para nome é de 100 caracteres');
      return false;
    }
    if (this.model.valor_diaria < 0.1) {
      this.chamarSnakBar('O valor da diária deve ser positivo');
      return false;
    }
    if (!this.model.valor_diaria) {
      this.chamarSnakBar('O valor da diária não deve ser nulo');
      return false;
    }
    if (!this.model.combustivel) {
      this.chamarSnakBar('O tipo do combustível deve ser informado');
      return false;
    }
    if (this.model.status === null) {
      this.chamarSnakBar('O status do veículo deve ser informado');
      return false;
    }
    return true;
  }

  chamarSnakBar(message: string): void {
    this.matSnack.open(message, undefined, { duration: 3000 });
  }

}
