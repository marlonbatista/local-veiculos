import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NgxSpinnerService } from 'ngx-spinner';
import Swal from 'sweetalert2';
import { IResultHttp } from '../Interfaces/ResultHttp';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(
    private http: HttpClient,
    private spinner: NgxSpinnerService) {

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

  public get(url: string): Promise<IResultHttp> {
    const header = this.createHeader();
    return new Promise(async (resolve) => {
      try {
        this.spinner.show();
        const res = await this.http.get(url, { headers: header }).toPromise();
        resolve({ success: true, data: res, error: undefined });
        this.spinner.hide();
      } catch (error) {
        this.spinner.hide();
        resolve({ success: false, data: {}, error });
      }
    });
  }

  public post(url: string, model: any, headers?: HttpHeaders): Promise<IResultHttp> {
    const header = this.createHeader(headers);
    return new Promise(async (resolve) => {
      try {
        this.spinner.show();
        const res = await this.http.post(url, model, { headers: header }).toPromise();
        resolve({ success: true, data: res, error: undefined });
        this.spinner.hide();
      } catch (error) {
        this.spinner.hide();
        if (error.status === 400) {
          console.log(error.error);
          let errorsText = '<ul>';
          if (Array.isArray(error.error)) {
            error.error.forEach(element => {
              errorsText += `<li style="text-align: left">${element.message || element}</li>`;
            });
            errorsText += '</ul>';
            Swal.fire('Atenção', errorsText, 'warning');
          }
        }
        resolve({ success: false, data: {}, error });
      }
    });
  }

  public delete(url: string): Promise<IResultHttp> {
    const header = this.createHeader();
    return new Promise(async (resolve) => {
      try {
        this.spinner.show();
        const res = await this.http.delete(url, { headers: header }).toPromise();
        resolve({ success: true, data: res, error: undefined });
        this.spinner.hide();
      } catch (error) {
        this.spinner.hide();
        resolve({ success: false, data: {}, error });
      }
    });
  }

}
