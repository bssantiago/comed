import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { IFile } from '../../shared/interfaces/Ifile';
import { IUserSearch, IUserInfo, IKeyValues } from '../../shared/interfaces/user-info';
import SharedConstants from '../../shared/constants';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class BiometricService {

  constructor(private httpClient: HttpClient) { }

  public getPrograms(): Observable<Array<IKeyValues>> {
    return this.httpClient
    .post(`${SharedConstants.url}/programs`, { withCredentials: true })
    .map((res: any) => {
      if (res.meta.errCode === 0) {
        return res.response;
      }
      throw (new Error());
    });
  }

  public getClients(): Observable<Array<IKeyValues>> {
    return this.httpClient
    .post(`${SharedConstants.url}/clients`, { withCredentials: true })
    .map((res: any) => {
      if (res.meta.errCode === 0) {
        return res.response;
      }
      throw (new Error());
    });
  }

  public getDrawTypes(): Observable<Array<IKeyValues>> {
    return this.httpClient
    .post(`${SharedConstants.url}/drawTypes`, { withCredentials: true })
    .map((res: any) => {
      if (res.meta.errCode === 0) {
        return res.response;
      }
      throw (new Error());
    });
  }

  public uploadFile(fileData: IFile): Observable<any> {
    return this.httpClient
      .post(`${SharedConstants.url}/authenticate`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        throw (new Error());
      });
  }

  public search(filter: IUserSearch): Observable<any> {
    return this.httpClient
      .post(`${SharedConstants.url}/authenticate`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        throw (new Error());
      });
  }

  public addUser(filter: IUserInfo): Observable<any> {
    return this.httpClient
      .post(`${SharedConstants.url}/authenticate`, { withCredentials: true })
      .map((res: any) => {
        if (res.meta.errCode === 0) {
          return res.response;
        }
        throw (new Error());
      });
  }

}
