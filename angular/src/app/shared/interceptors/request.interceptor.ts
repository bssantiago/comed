import { Injectable, Injector } from '@angular/core';
import { Router } from '@angular/router';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/do';
import { BlockUI, NgBlockUI } from 'ng-block-ui';


@Injectable()
export class RequestInterceptor {
    @BlockUI() blockUI: NgBlockUI;
    public defaultMessage = 'Please wait...';

    constructor(private router: Router,
        private injector: Injector) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req)
            .do(event => {
                this.blockUI.start(this.defaultMessage);
                if (event instanceof HttpResponse) {
                    this.blockUI.stop();
                    const redirect =  event.headers.get('RedirectTO');
                    if (redirect) {
                        window.location.href = redirect;
                    }
                    return event;
                }
            })
            .catch((err: HttpEvent<any>) => {
                this.blockUI.stop();
                if (err instanceof HttpErrorResponse) {
                    if (err.status === 401) {
                        return Observable.throw('401 Unauthorized');
                    } else if (err.status === 403) {
                    } else if (err.status === 307) {
                    } else if (err.status === 404) {
                    } else if (err.status === 500) {
                    } else {
                    }
                    return Observable.throw(err);
                }
            });
    }
}

