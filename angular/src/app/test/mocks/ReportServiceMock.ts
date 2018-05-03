import { Observable } from 'rxjs/Observable';

export class ReportServiceMock {
    public report(): Observable<any> {
        return new Observable((observer) => {

            const reportData = [];

            observer.next(reportData);
            observer.complete();
        });
    }
}
