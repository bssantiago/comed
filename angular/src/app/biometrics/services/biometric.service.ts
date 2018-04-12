import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { IFile } from '../../shared/interfaces/Ifile';
import { IUserSearch, IUserInfo, IKeyValues } from '../../shared/interfaces/user-info';
import SharedConstants from '../../shared/constants';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable()
export class BiometricService {

  constructor(private httpClient: HttpClient) { }

  public getPrograms(): Observable<Array<IKeyValues>> {
    return this.httpClient
      .get(`${SharedConstants.url}/programs`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        throw (new Error());
      });
  }

  public getClients(): Observable<Array<IKeyValues>> {
    return this.httpClient
      .get(`${SharedConstants.url}/clients`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        throw (new Error());
      });
  }

  public getDrawTypes(): Observable<Array<IKeyValues>> {
    return this.httpClient
      .get(`${SharedConstants.url}/drawTypes`, { withCredentials: true })
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

  public search(filter: IUserSearch): Observable<any> {
    return this.httpClient
      .get(`${environment.apiUrl}search`, { withCredentials: true })
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

}
