
import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { Cliente } from '../Interfaces/Cliente';
import { BaseService } from './base';


@Injectable({
  providedIn: 'root'
})
export class ClienteService extends BaseService<Cliente> {

  constructor(public http: HttpService) {
    super('cliente/', http);
  }
}
