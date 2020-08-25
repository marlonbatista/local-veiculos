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

  validarCampos(): boolean {
    if (!this.model.cpf) {
      this.chamarSnakBar('O cpf não deve ser nulo');
      return false;
    }
    if (this.model.cpf.length !== 11) {
      this.chamarSnakBar('O cpf deve conter 11 caracteres');
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
