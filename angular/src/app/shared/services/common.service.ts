import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs/Observable';
import { ToastrService } from 'ngx-toastr';

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

  public exitPlatform(): Observable<Boolean> {
    return this.httpClient
      .delete(`${environment.apiUrlPublic}authenticate/exit`)
      .map((res: any) => {
        return true;
      }).catch(this.serverError);
  }

}
