import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs/Observable';
import { ToastrService } from 'ngx-toastr';
import { filter } from 'lodash';

@Injectable()
export class CommonService {
  private error = 'Error trying to perform ';

  constructor(private httpClient: HttpClient, public toastr: ToastrService) { }

  private serverError(err: any) {
    let result: Observable<Boolean>;
    if (err instanceof Response) {
      err = err.json() || 'backend server error';
      result = Observable.throw(err.json() || 'backend server error');
    }
    result = Observable.throw(err || 'backend server error');
    this.toastr.error(err);
    return result;
  }

  public authenticate(header: any): Observable<any> {
    return this.httpClient
      .post(`${environment.apiUrlPublic}authenticate`, {}, { headers: header, withCredentials: true })
      .map((res: Response) => {
        console.log(res.headers);
      })
      .catch(this.serverError);
  }


  public exitPlatform(): Observable<Boolean> {
    return this.httpClient
      .delete(`${environment.apiUrlPublic}authenticate/exit`, { withCredentials: true })
      .map((res: any) => {
        return true;
      }).catch(this.serverError);
  }

  public getPeople(name: string): Observable<any> {
    const people = [{
      name: 'Alberto',
      id: 'Al',
    },
    {
      name: 'NichuCome',
      id: 'Ni',
    }];

    const search = filter(people, (p: any) => {
      return p.name.indexOf(name) >= 0;
    });

    return new Observable((observer) => {
      observer.next(search);
      observer.complete();
    });
  }

}
