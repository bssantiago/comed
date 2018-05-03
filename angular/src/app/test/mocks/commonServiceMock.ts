import { Observable } from 'rxjs/Observable';

export class CommonServiceMock {
    public authenticate(): Observable<any> {
        return new Observable((observer) => {

            const headers = 'headers';

            observer.next(headers);
            observer.complete();
        });
    }
}
