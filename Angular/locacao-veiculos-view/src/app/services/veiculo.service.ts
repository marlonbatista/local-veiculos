
import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { BaseService } from './base';
import { Veiculo } from '../Interfaces/Veiculo';


@Injectable({
  providedIn: 'root'
})
export class VeiculoService extends BaseService<Veiculo> {

  constructor(public http: HttpService) {
    super('veiculo/', http);
  }
}
