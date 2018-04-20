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

@Injectable()
export class BiometricService {

  constructor(private httpClient: HttpClient) { }

  public markAsDownloaded(model: any): Observable<Boolean> {
    return this.httpClient
      .post(`${environment.apiUrl}client_assessment/markAsDownload`, model, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        throw (new Error());
      });
  }

  public getPrograms(): Observable<Array<IKeyValues>> {
    return this.httpClient
      .get(`${environment.apiUrl}/programs`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
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
        throw (new Error());
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
              reward_date: data.reward_date
            };
          });
        }
        throw (new Error());
      });
  }

  public getDrawTypes(): Observable<Array<IKeyValues>> {
    return this.httpClient
      .get(`${environment.apiUrl}/drawTypes`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {

          return res.response;
        }
        throw (new Error());
      });
  }

  public getUserInfo(id: number): Observable<any> {
    return this.httpClient
      .get(`${SharedConstants.localUrl}/biometrics/${id}`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        throw (new Error());
      });
  }

  public uploadFile(fileData: IFile): Observable<any> {
    return this.httpClient
      .post(`${SharedConstants.localUrl}/client_assessment`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
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
        throw (new Error());
      });
  }

  public getLastNames(lastname: string): Observable<any> {
    return this.httpClient
      .get(`${environment.apiUrl}participant/lastnames/${lastname}`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        throw (new Error());
      });
  }

  public getFirstNames(firstname: string): Observable<any> {
    return this.httpClient
      .get(`${environment.apiUrl}participant/firstnames/${firstname}`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        throw (new Error());
      });
  }

  public save(model: IUserInfo): Observable<any> {
    return this.httpClient
      .post(`${SharedConstants.localUrl}/biometrics`, model, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        throw (new Error());
      });
  }

  public update(model: IUserInfo): Observable<any> {
    return this.httpClient
      .put(`${SharedConstants.localUrl}/biometrics`, model, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        throw (new Error());
      });
  }

  public uploadFile2(request: any): Observable<any> {
    return this.httpClient
      .post(`${environment.apiUrl}client_assessment`, request, { withCredentials: true })
      .map((res: any) => {
        return res.result;
      });
  }

}
