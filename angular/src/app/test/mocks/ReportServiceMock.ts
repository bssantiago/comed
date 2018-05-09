import { Observable } from 'rxjs/Observable';

export class ReportServiceMock {

    public report(): Observable<any> {
        return new Observable((observer) => {

            const reportData = [];

            observer.next(reportData);
            observer.complete();
        });
    }

    public health(id: number): Observable<any> {
        return new Observable((observer) => {

            const reportData = {
                sistolic: []
            };

            observer.next(reportData);
            observer.complete();
        });
    }

    public history(id: number, type: string): Observable<any> {
        return new Observable((observer) => {

            const reportData = [];

            observer.next(reportData);
            observer.complete();
        });
    }
}
