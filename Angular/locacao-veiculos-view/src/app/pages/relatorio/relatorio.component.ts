import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatSnackBar } from '@angular/material';
import AluguelCliente from 'src/app/Interfaces/AluguelCLiente';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { AluguelService } from 'src/app/services/aluguel.serve';

@Component({
  selector: 'app-relatorio',
  templateUrl: './relatorio.component.html',
  styleUrls: ['./relatorio.component.scss']
})
export class RelatorioComponent implements OnInit {

  displayedColumns: string[] = [
    'cod_aluguel',
    'cod_cliente',
    'cod_veiculo',
    'data_devolucao',
    'data_locacao',
    'nome_cliente',
    'status',
    'valor_total',
  ];
  dataSource: MatTableDataSource<AluguelCliente>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  model: AluguelCliente = {
    cod_aluguel: null,
    cod_cliente: null,
    cod_veiculo: null,
    data_devolucao: null,
    data_locacao: null,
    nome_cliente: '',
    status: false,
    valor_total: null
  };

  list: AluguelCliente[] = [this.model];

  constructor(
    private aluguelSrv: AluguelService,
    private matSnack: MatSnackBar,
    private router: Router,
    private spinner: NgxSpinnerService
  ) {
    this.dataSource = new MatTableDataSource(this.list);
  }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  async buscarAluguel(): Promise<void> {
    if (this.model.cod_cliente === null) {
      this.chamarSnakBar('Informe o código do aluguel');
      return;
    }
    this.spinner.show();
    const result = await this.aluguelSrv.GetAluguelById('' + this.model.cod_cliente);
    console.log(result.data);
    if (!result.success || !result.data) {
      this.spinner.hide();
      this.chamarSnakBar('Aluguel não localizado');
    } else {
      this.list = result.data;
      this.dataSource = new MatTableDataSource(this.list);
      this.spinner.hide();
    }
  }

  chamarSnakBar(message: string): void {
    this.matSnack.open(message, undefined, { duration: 3000 });
  }

}
