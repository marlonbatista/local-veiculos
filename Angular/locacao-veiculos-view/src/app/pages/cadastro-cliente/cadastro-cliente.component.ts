import { Component, OnInit } from '@angular/core';
import { Cliente } from 'src/app/Interfaces/Cliente';
import { MatCalendarCellCssClasses, MatSnackBar, MatSnackBarConfig } from '@angular/material';
import { ClienteService } from 'src/app/services/cliente.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-cadastro-cliente',
  templateUrl: './cadastro-cliente.component.html',
  styleUrls: ['./cadastro-cliente.component.scss']
})
export class CadastroClienteComponent implements OnInit {

  model: Cliente = {
    cod_cliente: null,
    nome: '',
    cpf: '',
    data_nascimento: new Date(),
    status: false,
  };

  constructor(
    private clienteSrv: ClienteService,
    private matSnack: MatSnackBar,
    private router: Router,
    private spinner: NgxSpinnerService
  ) { }

  ngOnInit() { }

  async buscarCliente(): Promise<void> {
    if (this.model.cod_cliente === null) {
      this.matSnack.open('Informe o código do cliente', undefined, { duration: 3000 });
      return;
    }
    this.spinner.show();
    const result = await this.clienteSrv.GetById(this.model.cod_cliente);
    if (!result.success || !result.data) {
      this.spinner.hide();
      this.matSnack.open('Cliente não localizado', undefined, { duration: 3000 });
    } else {
      this.model = result.data;
      this.spinner.hide();
    }
  }

  async save(): Promise<void> {
    if (!this.validarCampos()) {
      return;
    }
    const result = await this.clienteSrv.post(this.model);
    if (result.success) {
      if (this.model.cod_cliente !== null) {
        this.matSnack.open('Cliente alterado com sucesso', undefined, { duration: 3000 });
      } else {
        this.matSnack.open('Cliente salvo com sucesso', undefined, { duration: 3000 });
      }
      this.model = result.data;
    }
  }

  async delete(): Promise<void> {
    if (this.model.cod_cliente !== null) {
      const result = await this.clienteSrv.delete('' + this.model.cod_cliente);
      if (result.success) {
        this.matSnack.open('Cliente excluído com sucesso', undefined, { duration: 3000 });
      } else {
        this.matSnack.open('Não foi possível excluir o cliente', undefined, { duration: 3000 });
      }
    } else {
      this.matSnack.open('Informe um código de cliente válido', undefined, { duration: 3000 });
    }
  }

  limparModel(): void {
    this.model = {
      cod_cliente: null,
      nome: '',
      cpf: '',
      data_nascimento: new Date(),
      status: false,
    };
  }

  validarCPF(cpf: string): boolean {
    if (cpf == null) {
      this.chamarSnakBar('O cpf não deve nullo');
      return false;
    }
    if (cpf.length != 11) {
      this.chamarSnakBar('O cpf deve conter 11 caracteres');
      return false;
    }

    if (cpf == '00000000000' ||
      cpf == '11111111111' ||
      cpf == '22222222222' ||
      cpf == '33333333333' ||
      cpf == '44444444444' ||
      cpf == '55555555555' ||
      cpf == '66666666666' ||
      cpf == '77777777777' ||
      cpf == '88888888888' ||
      cpf == '99999999999')
      return false;

    let numero: number = 0;
    let caracter: string = '';
    let numeros: string = '0123456789';
    let j: number = 10;
    let somatorio: number = 0;
    let resto: number = 0;
    let digito1: number = 0;
    let digito2: number = 0;
    let cpfAux: string = '';
    cpfAux = cpf.substring(0, 9);
    for (let i: number = 0; i < 9; i++) {
      caracter = cpfAux.charAt(i);
      if (numeros.search(caracter) == -1) {
        return false;
      }
      numero = Number(caracter);
      somatorio = somatorio + (numero * j);
      j--;
    }
    resto = somatorio % 11;
    digito1 = 11 - resto;

    if (digito1 > 9)
      digito1 = 0;

    j = 11;
    somatorio = 0;
    cpfAux = cpfAux + digito1;

    for (let i: number = 0; i < 10; i++) {
      caracter = cpfAux.charAt(i);
      numero = Number(caracter);
      somatorio = somatorio + (numero * j);
      j--;
    }
    resto = somatorio % 11;
    digito2 = 11 - resto;

    if (digito2 > 9)
      digito2 = 0;

    cpfAux = cpfAux + digito2;

    if (cpf != cpfAux) {
      this.chamarSnakBar('O cpf informado não é valido');
      return false;
    }
    else
      return true;
  }

  validarCampos(): boolean {
    if (!this.validarCPF(this.model.cpf)) {
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

    if (!this.model.data_nascimento) {
      this.chamarSnakBar('A data de nascimento deve ser informada');
      return false;
    }
    return true;
  }

  chamarSnakBar(message: string): void {
    this.matSnack.open(message, undefined, { duration: 3000 });
  }

}
