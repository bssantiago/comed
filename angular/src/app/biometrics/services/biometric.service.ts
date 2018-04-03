import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { IFile } from '../../shared/interfaces/Ifile';
import { IUserSearch, IUserInfo } from '../../shared/interfaces/user-info';
import SharedConstants from '../../shared/constants';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class BiometricService {

  constructor(private httpClient: HttpClient) { }

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
