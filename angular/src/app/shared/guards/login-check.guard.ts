import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { CookieService } from 'ngx-cookie-service';
import { isNil } from 'lodash';

@Injectable()
export class LoginCheckGuard implements CanActivate {

  constructor(private cookieService: CookieService) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return true;
  }

  checkLogin(url: string): boolean {
    return !isNil(this.cookieService.get('Test'));
  }
}
