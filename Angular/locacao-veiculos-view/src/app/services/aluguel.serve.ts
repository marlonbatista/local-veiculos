
import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { BaseService } from './base';
import Aluguel from '../Interfaces/Aluguel';
import { environment } from 'src/environments/environment';
import { IResultHttp } from '../Interfaces/ResultHttp';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { NgxSpinnerService } from 'ngx-spinner';


@Injectable({
  providedIn: 'root'
})
export class AluguelService extends BaseService<Aluguel> {

  constructor(
    public http: HttpService,
    private httpC: HttpClient,
    private spinner: NgxSpinnerService) {
    super('aluguel/', http);
  }


  GetDeliveryByIdAluguel(id: string): Promise<IResultHttp> {
    return this.http.get(`${environment.url_api}/aluguel/devolucao/${id}`);
  }

  GetAluguelById(id: string): Promise<IResultHttp> {
    return this.http.get(`${environment.url_api}/aluguel/cliente/${id}`);
  }

  deliver(model: any, headers?: HttpHeaders): Promise<IResultHttp> {
    const header = this.createHeader(headers);
    const url = `${environment.url_api}/aluguel/`;
    return new Promise(async (resolve) => {
      try {
        this.spinner.show();
        const res = await this.httpC.post(url, model, { headers: header }).toPromise();
        resolve({ success: true, data: res, error: undefined });
        this.spinner.hide();
      } catch (error) {
        this.spinner.hide();
        if (error.status === 400) {
          console.log(error.error);
        }
        resolve({ success: false, data: {}, error });
      }
    });
  }

  private createHeader(header?: HttpHeaders): HttpHeaders {

    if (!header) {
      header = new HttpHeaders();
    }

    header = header.append('Access-Control-Allow-Origin', '*');
    header = header.append('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE');
    header = header.append('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
    header = header.append('Content-Type', 'application/json');
    header = header.append('Accept', 'application/json');

    return header;
  }
}
