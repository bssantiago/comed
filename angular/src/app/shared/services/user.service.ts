import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import 'rxjs/add/operator/map';

@Injectable()
export class UserService {

  private baseUserUrl = 'http://localhost:3000';

  constructor(private httpClient: HttpClient) { }

  public login(email: string, password: string) {
    console.log('llego');
    return this.httpClient.post(`${this.baseUserUrl}/pdf`, {}).subscribe(
      (res: any) => {
        const item = 'data:application/octet-stream;base64,' + res.buffer;
        const blowItem = new Blob([item], { type: 'application/pdf' });
        const fileURL = URL.createObjectURL(blowItem);
        window.open(item);
      },
      err => { console.log('Error occured'); }
    );

  }

}
