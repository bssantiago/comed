import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { CookieService } from 'ngx-cookie-service';
import { isNil } from 'lodash';

@Injectable()
export class LoginCheckGuard implements CanActivate {

  constructor(private cookieService: CookieService, private route: Router) { }

  public canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    if (this.checkLogin()) {
      return true;
    } else {
      this.route.navigate(['./home']);
    }
    return false;
  }

  private checkLogin(): boolean {
    console.log(this.cookieService.get('comed-cookie'));
    return true;
    // return !this.isNilOrEmpty(this.cookieService.get('comed-cookie'));
  }

  private isNilOrEmpty(object: any): boolean {
    return isNil(object) || object === '';
  }

}
