import { Injectable, Injector } from '@angular/core';
import { Router } from '@angular/router';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/do';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ToastrService, ToastrConfig } from 'ngx-toastr';
import { LocalStorageService } from '../services/local-storage.service';


@Injectable()
export class RequestInterceptor {
    @BlockUI() blockUI: NgBlockUI;
    public defaultMessage = 'Please wait...';
    public genericMessage = 'Error ocurred, pelase contact with your adminsitrator';

    constructor(private router: Router,
        public toastr: ToastrService,
        private injector: Injector,
        private localStorageService: LocalStorageService) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const islastnameOrName = req.url.indexOf('/comed/rest/private/participant/lastnames/') >= 0
            || req.url.indexOf('/comed/rest/private/participant/firstnames/') >= 0;
        return next.handle(req)
            .do(event => {
                if (!islastnameOrName) {
                    this.blockUI.start(this.defaultMessage);
                }

                if (event instanceof HttpResponse) {
                    if (!islastnameOrName) {
                        this.blockUI.stop();
                    }
                    const redirect = event.headers.get('RedirectTO');
                    if (redirect) {
                        window.location.href = redirect;
                        const clientId = event.headers.get('clientId');
                        if (clientId) {
                            this.localStorageService.setClientId(clientId);
                        } else {
                            this.localStorageService.removeClientId();
                         }
                    }
                    return event;
                }
            })
            .catch((err: HttpEvent<any>) => {
                this.blockUI.stop();
                if (err instanceof HttpErrorResponse) {
                    if (err.status === 401) {
                        this.router.navigate([`forbidden`]);
                        return Observable.throw('401 Unauthorized');
                    } else if (err.status === 403) {
                        this.toastr.error(null, this.genericMessage);
                        this.router.navigate([`forbidden`]);
                    } else if (err.status === 307) {
                    } else if (err.status === 404) {
                        if (err.error) {
                            this.toastr.error(null, `method ${req.url} not found on server`);
                        } else {
                            this.toastr.error(null, this.genericMessage);
                        }
                    } else if (err.status === 500) {
                        this.toastr.error(null, this.genericMessage);
                    } else {
                        this.toastr.error(null, this.genericMessage);
                    }
                    return Observable.throw(err);
                }
            });
    }
}

