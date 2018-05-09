import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class ReportService {

  constructor(private httpClient: HttpClient) { }

  public report(): Observable<any> {
    return this.httpClient
      .post(`${environment.apiUrl}reports`, {}, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        throw (new Error());
      });
  }

  public health(id: number): Observable<any> {
    return this.httpClient
      .post(`${environment.apiUrl}reports/health`, {
        participant_id: id
      }, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        throw (new Error());
      });
  }

  public history(id: number, type: string): Observable<any> {
    return this.httpClient
      .post(`${environment.apiUrl}reports/history/${type}`, {
        participant_id: id
      }, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        throw (new Error());
      });
  }


}
