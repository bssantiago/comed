import { Observable } from 'rxjs/Observable';
import { IClient } from '../../shared/interfaces/IClientInfo';
import { IFile, IFileTable } from '../../shared/interfaces/Ifile';
import { IParticipantSearch } from '../../shared/interfaces/participant-info';
import { ISearch } from '../../shared/interfaces/ISearch';
import { IUserInfo } from '../../shared/interfaces/user-info';
import { IListTableItems } from '../../shared/interfaces/ITable';

export class BiometricsServiceMock {


    public markAsDownloaded(model: any): Observable<Boolean> {
        return new Observable((observer) => {
            observer.next(true);
            observer.complete();
        });
    }

    public bindPatientWithClient(model: any): Observable<any> {
        return new Observable((observer) => {
            observer.next(true);
            observer.complete();
        });
    }

    public setParticipant(model: any): Observable<any> {
        return new Observable((observer) => {
            observer.next(true);
            observer.complete();
        });
    }

    public getText(): Observable<any> {
        return new Observable((observer) => {
            observer.next(true);
            observer.complete();
        });
    }

    public getPdf(id: number): Observable<any> {
        return new Observable((observer) => {
            observer.next(true);
            observer.complete();
        });
    }

    public getClients(): Observable<Array<IClient>> {
        return new Observable((observer) => {
            observer.next([
                {
                    id: 1,
                    name: 'c1',
                    program: 'p1',
                    reward_date: new Date()
                }, {
                    id: 2,
                    name: 'c2',
                    program: 'p2',
                    reward_date: new Date()
                },
                {
                    id: 3,
                    name: 'c3',
                    program: 'p3',
                    reward_date: new Date()
                }]);
            observer.complete();
        });
    }

    public getUserInfo(id: number): Observable<IUserInfo> {
        return new Observable((observer) => {
            const userInfo: IUserInfo = {
                date_of_birth: new Date(),
                first_name: '',
                last_name: '',
                member_id: 123,
                program_display_name: '',
                reward_date: new Date(),
                body_fat: 0,
                cholesterol: 0,
                diastolic: 0,
                duration: 0,
                fasting: false,
                glucose: 0,
                hba1c: 0,
                hdl: 0,
                ldl: 0,
                sistolic: 0,
                tobacco_use: false,
                triglycerides: 0,
                waist: 0,
                weight: 0,
                height: 0
            };

            observer.next(userInfo);
            observer.complete();
        });
    }

    public uploadFile(fileData: IFile): Observable<any> {
        return new Observable((observer) => {
            observer.next(true);
            observer.complete();
        });
    }

    public search(filter: IParticipantSearch): Observable<any> {
        return new Observable((observer) => {
            observer.next(true);
            observer.complete();
        });
    }

    public getClientAssessments(search: ISearch): Observable<IListTableItems<IFileTable>> {
        return new Observable((observer) => {
            const table: IListTableItems<IFileTable> = {
                pages: 1,
                items: [
                    {
                        client_name: 'client1',
                        file_name: 'file1',
                        program_display_name: 'p1',
                    },
                    {
                        client_name: 'client2',
                        file_name: 'file2',
                        program_display_name: 'p2',
                    }
                ]
            };
            observer.next(table);
            observer.complete();
        });
    }

    public getLastNames(lastname: string): Observable<any> {
        return new Observable((observer) => {
            observer.next(true);
            observer.complete();
        });
    }

    public getFirstNames(firstname: string): Observable<any> {
        return new Observable((observer) => {
            observer.next(true);
            observer.complete();
        });
    }

    public save(model: IUserInfo): Observable<any> {
        return new Observable((observer) => {
            observer.next(true);
            observer.complete();
        });
    }

    public update(model: IUserInfo): Observable<any> {
        return new Observable((observer) => {
            observer.next(true);
            observer.complete();
        });
    }

    public saveBiometric(model: IUserInfo): Observable<any> {
        return new Observable((observer) => {
            observer.next(true);
            observer.complete();
        });
    }

    public upload(request: any): Observable<any> {
        return new Observable((observer) => {
            observer.next(true);
            observer.complete();
        });
    }
}
