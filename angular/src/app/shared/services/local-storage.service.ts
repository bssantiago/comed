import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import 'rxjs/add/operator/map';

@Injectable()
export class LocalStorageService {
  private CLIENT = 'client';
  public clientId: string;

  constructor() { }

  public setClientId(clientId: string) {
    localStorage.setItem(this.CLIENT, clientId);
  }

  public getClientId(): string {
    return localStorage.getItem(this.CLIENT);
  }

  public removeClientId() {
    localStorage.removeItem(this.CLIENT);
  }
}
