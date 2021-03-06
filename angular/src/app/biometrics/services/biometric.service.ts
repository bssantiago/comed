import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { IFile } from '../../shared/interfaces/Ifile';
import { IUserInfo, IKeyValues } from '../../shared/interfaces/user-info';
import SharedConstants from '../../shared/constants';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { IParticipantSearch } from '../../shared/interfaces/participant-info';
import { ISearch } from '../../shared/interfaces/ISearch';
import { map } from 'lodash';
import { IClient } from '../../shared/interfaces/IClientInfo';
import { ToastrService, ToastrConfig } from 'ngx-toastr';

@Injectable()
export class BiometricService {

  private error = 'Error trying to perform ';

  constructor(private httpClient: HttpClient, public toastr: ToastrService) { }


  public markAsDownloaded(model: any): Observable<Boolean> {
    return this.httpClient
      .post(`${environment.apiUrl}client_assessment/markAsDownload`, model, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        this.toastr.error(null, `${this.error}download`);
        throw (new Error());
      });
  }

  public bindPatientWithClient(model: any): Observable<any> {
    return this.httpClient
      .post(`${environment.apiUrl}participant/bindParticipantClient`, model, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res;
        }
        this.toastr.error(null, `${res.meta.msg}`);
        throw (new Error());
      });
  }

  public setParticipant(model: any): Observable<any> {
    return this.httpClient
      .post(`${environment.apiUrl}participant/setParticipant`, model, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        this.toastr.error(null, `${this.error}adding new participant`);
        throw (new Error());
      });
  }

  public getText(): Observable<any> {
    return this.httpClient
      .get(`${environment.apiUrl}participant/file`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          const blob = new Blob([atob(res.response)], { type: 'text/csv' });
          const url = window.URL.createObjectURL(blob);
          window.open(url);
          return res.response;
        }
        this.toastr.error(null, `${this.error}file`);
        throw (new Error());
      });
  }

  public getPdf(id: number): Observable<any> {
    return this.httpClient
      .get(`${environment.apiUrl}participant/pdf?participant_id=${id}`, { withCredentials: true })
      .map((res: any) => {

      });
  }

  public getClients(): Observable<Array<IClient>> {
    return this.httpClient
      .get(`${environment.apiUrl}clients`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return map(res.response, (data: any) => {
            return {
              id: data.id,
              name: data.name,
              program: data.program_display_name,
              program_id: data.program_id,
              reward_date: data.reward_date
            };
          });
        }
        this.toastr.error(null, `${this.error}get clients`);
        throw (new Error());
      });
  }

  public getUserInfo(id: number): Observable<any> {
    return this.httpClient
      .get(`${environment.apiUrl}biometrics/${id}`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        this.toastr.error(null, `${this.error}get user info`);
        throw (new Error());
      });
  }

  public search(filter: IParticipantSearch): Observable<any> {
    return this.httpClient
      .post(`${environment.apiUrl}participant/search`, filter, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        this.toastr.error(null, `${this.error}search`);
        throw (new Error());
      });
  }

  public getClientAssessments(search: ISearch) {
    return this.httpClient
      .post(`${environment.apiUrl}client_assessment/search`, search, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        this.toastr.error(null, `${this.error}get client assessments`);
        throw (new Error());
      });
  }

  public getLastNames(lastname: string, client: number): Observable<any> {
    return this.httpClient
      .get(`${environment.apiUrl}participant/lastnames/${client}/${lastname}`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        this.toastr.error(null, `${this.error}get lastnames`);
        throw (new Error());
      });
  }

  public getFirstNames(firstname: string, client: number): Observable<any> {
    return this.httpClient
      .get(`${environment.apiUrl}participant/firstnames/${client}/${firstname}`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        this.toastr.error(null, `${this.error}get firstnames`);
        throw (new Error());
      });
  }

  public update(model: IUserInfo): Observable<any> {
    return this.httpClient
      .put(`${environment.apiUrl}biometrics`, model, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        this.toastr.error(null, `${this.error}update biometrics`);
        throw (new Error());
      });
  }

  public saveBiometric(model: IUserInfo): Observable<any> {
    return this.httpClient
      .post(`${environment.apiUrl}biometrics`, model, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        this.toastr.error(null, `${this.error}create biometrics`);
        if (res.meta.msg) {
          this.toastr.error(null, res.meta.msg);
        }
        throw (new Error(res.meta.msg));
      });
  }

  public upload(request: any): Observable<any> {
    return this.httpClient
      .post(`${environment.apiUrl}client_assessment`, request, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          this.toastr.success('File uploaded', 'Success');
        } else {
          this.toastr.error(res.meta.msg, 'Error');
        }
        return res;
      });
  }

}
