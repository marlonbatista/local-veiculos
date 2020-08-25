import { Component, OnInit } from '@angular/core';
import { Menu } from './Interfaces/Menu';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  title = 'locacao-veiculos-view';

  menu: Array<Menu> = new Array<Menu>();

  constructor() {

  }
  ngOnInit(): void {
    this.menu.push({
      group: 'Clientes',
      items: [
        { icon: 'person_pin', label: 'Cadastro', url: 'home/cadastro-cliente' },
      ]
    });

    this.menu.push({
      group: 'Veiculos',
      items: [
        { icon: 'directions_car', label: ' Cadastro de Veiculos', url: 'home/cadastro-veiculo' },
      ]
    });

    this.menu.push({
      group: 'Aluguel',
      items: [
        { icon: 'local_atm', label: 'Aluguel', url: 'home/registro-aluguel' },
        { icon: 'local_atm', label: 'Devolução', url: 'home/devolucao' }
      ]
    });

    this.menu.push({
      group: 'Relatório',
      items: [
        { icon: 'recent_actors', label: 'Relatório', url: 'home/relatorio' }
      ]
    });
  }
}
