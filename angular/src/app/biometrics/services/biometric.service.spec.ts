import { TestBed, getTestBed, async, inject } from '@angular/core/testing';

import { BiometricService } from './biometric.service';
import { ToastrModule } from 'ngx-toastr';
import { SharedModule } from '../../shared/shared.module';
import { HttpClientModule, HttpRequest, HttpParams } from '@angular/common/http';

import { RouterTestingModule } from '@angular/router/testing';
import { Http, BaseRequestOptions, XHRBackend, ResponseOptions, Response, HttpModule } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { environment } from '../../../environments/environment';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { IClient } from '../../shared/interfaces/IClientInfo';
import { IUserInfo } from '../../shared/interfaces/user-info';

describe('BiometricService', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        HttpClientTestingModule,
        SharedModule,
        ToastrModule.forRoot(),
      ],
      providers: [
        BiometricService
      ]
    });
    const testbed = getTestBed();

  }));


  it(`should send an expected setParticipant request`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.setParticipant({}).subscribe((next) => {
        expect(next).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}participant/setParticipant`).flush({
        meta: {
          errCode: 0,
          msg: ''
        },
        response: true
      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected setParticipant error`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.setParticipant({}).subscribe((next) => {
        expect(next).toBeTruthy();
      },
        err => {
          expect(err).toBeTruthy();
          expect(err).toBeTruthy();
        });

      backend.expectOne(`${environment.apiUrl}participant/setParticipant`).flush({
        meta: {
          errCode: -1,
          msg: 'error'
        },
        response: true
      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected markAsDownload request`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.markAsDownloaded({}).subscribe((next) => {
        expect(next).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}client_assessment/markAsDownload`).flush({
        meta: {
          errCode: 0,
          msg: ''
        },
        response: true
      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected markAsDownload error`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.markAsDownloaded({}).subscribe((next) => {
        expect(next).toBeTruthy();
      },
        err => {
          expect(err).toBeTruthy();
          expect(err).toBeTruthy();
        });

      backend.expectOne(`${environment.apiUrl}client_assessment/markAsDownload`).flush({
        meta: {
          errCode: -1,
          msg: 'error'
        },
        response: true
      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected bindParticipantClient request`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.bindPatientWithClient({}).subscribe((next) => {
        expect(next).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}participant/bindParticipantClient`).flush({
        meta: {
          errCode: 0,
          msg: ''
        },
        response: true
      }, { status: 200, statusText: 'Ok' });
    })));


  it(`should send an expected bindParticipantClient error`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.bindPatientWithClient({}).subscribe((next) => {
        expect(next).toBeTruthy();
      }, err => {
        expect(err).toBeTruthy();
        expect(err).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}participant/bindParticipantClient`).flush({
        meta: {
          errCode: -1,
          msg: 'error'
        },
        response: true
      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected getText request`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      const windowOpenSpy = spyOn(window, 'open');
      service.getText().subscribe((next) => {
        expect(next).toBeTruthy();
        expect(windowOpenSpy).toHaveBeenCalled();
      });

      backend.expectOne(`${environment.apiUrl}participant/file`).flush({
        meta: {
          errCode: 0,
          msg: ''
        },
        response: true
      }, { status: 200, statusText: 'Ok' });
    })));


  it(`should send an expected getText error`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      const windowOpenSpy = spyOn(window, 'open');
      service.getText().subscribe((next) => {
        expect(next).toBeTruthy();
        expect(windowOpenSpy).toHaveBeenCalled();
      }, err => {
        expect(err).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}participant/file`).flush({
        meta: {
          errCode: -1,
          msg: 'error'
        },
        response: true
      }, { status: 200, statusText: 'Ok' });
    })));


  it(`should send an expected getClients request`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.getClients().subscribe((next: Array<IClient>) => {
        expect(next).toBeTruthy();
        expect(next.length).toBe(3);
      });

      backend.expectOne(`${environment.apiUrl}clients`).flush({
        meta: {
          errCode: 0,
          msg: ''
        },
        response: [
          { id: 1, name: 'c1', program_display_name: '22', program_id: '1', reward_date: new Date() },
          { id: 2, name: 'c2', program_display_name: '23', program_id: '2', reward_date: new Date() },
          { id: 3, name: 'c3', program_display_name: '24', program_id: '3', reward_date: new Date() }
        ]
      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected getClients error`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.getClients().subscribe((next: Array<IClient>) => {
        expect(next).toBeTruthy();
        expect(next.length).toBe(3);
      }, err => {
        expect(err).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}clients`).flush({
        meta: {
          errCode: -1,
          msg: ''
        },
        response: []
      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected getUserInfo request`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.getUserInfo(1).subscribe((next) => {
        expect(next).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}biometrics/1`).flush({
        meta: {
          errCode: 0,
          msg: ''
        },
        response: { id: 3, name: 'c3', program_display_name: '24', program_id: '3', reward_date: new Date() }

      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected getUserInfo error`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.getUserInfo(1).subscribe((next) => {
        expect(next).toBeTruthy();
      }, err => {
        expect(err).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}biometrics/1`).flush({
        meta: {
          errCode: -1,
          msg: 'error'
        },
        response: { id: 3, name: 'c3', program_display_name: '24', program_id: '3', reward_date: new Date() }

      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected search request`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.search({}).subscribe((next) => {
        expect(next).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}participant/search`).flush({
        meta: {
          errCode: 0,
          msg: ''
        },
        response: { id: 3, name: 'c3', program_display_name: '24', program_id: '3', reward_date: new Date() }

      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected search error`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.search({}).subscribe((next) => {
        expect(next).toBeTruthy();
      }, err => {
        expect(err).toBeTruthy();
      });


      backend.expectOne(`${environment.apiUrl}participant/search`).flush({
        meta: {
          errCode: -1,
          msg: 'error'
        },
        response: { id: 3, name: 'c3', program_display_name: '24', program_id: '3', reward_date: new Date() }

      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected getClientAssessments request`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.getClientAssessments({}).subscribe((next) => {
        expect(next).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}client_assessment/search`).flush({
        meta: {
          errCode: 0,
          msg: ''
        },
        response: {}

      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected getClientAssessments error`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.getClientAssessments({}).subscribe((next) => {
        expect(next).toBeTruthy();
      }, err => {
        expect(err).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}client_assessment/search`).flush({
        meta: {
          errCode: -1,
          msg: 'error'
        },
        response: {}

      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected getLastNames request`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.getLastNames('a', 1).subscribe((next) => {
        expect(next).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}participant/lastnames/1/a`).flush({
        meta: {
          errCode: 0,
          msg: ''
        },
        response: []

      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected getLastNames error`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.getLastNames('a', 1).subscribe((next) => {
        expect(next).toBeTruthy();
      }, err => {
        expect(err).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}participant/lastnames/1/a`).flush({
        meta: {
          errCode: -1,
          msg: 'error'
        },
        response: []

      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected getFirstNames request`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.getFirstNames('a', 1).subscribe((next) => {
        expect(next).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}participant/firstnames/1/a`).flush({
        meta: {
          errCode: 0,
          msg: ''
        },
        response: []

      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected getFirstNames error`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      service.getFirstNames('a', 1).subscribe((next) => {
        expect(next).toBeTruthy();
      }, err => {
        expect(err).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}participant/firstnames/1/a`).flush({
        meta: {
          errCode: -1,
          msg: 'error'
        },
        response: []

      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected update request`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      const user: IUserInfo = {
        body_fat: 1, height: 1, cholesterol: 1, diastolic: 1, duration: 1, fasting: false, glucose: 1, hba1c: 1, hdl: 1, ldl: 1,
        sistolic: 1, tobacco_use: false, triglycerides: 1, waist: 1, weight: 1
      };
      service.update(user).subscribe((next) => {
        expect(next).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}biometrics`).flush({
        meta: {
          errCode: 0,
          msg: ''
        },
        response: []

      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected update error`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      const user: IUserInfo = {
        body_fat: 1, height: 1, cholesterol: 1, diastolic: 1, duration: 1, fasting: false, glucose: 1, hba1c: 1, hdl: 1, ldl: 1,
        sistolic: 1, tobacco_use: false, triglycerides: 1, waist: 1, weight: 1
      };
      service.update(user).subscribe((next) => {
        expect(next).toBeTruthy();
      }, err => {
        expect(err).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}biometrics`).flush({
        meta: {
          errCode: -1,
          msg: 'error'
        },
        response: []

      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected saveBiometric request`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      const user: IUserInfo = {
        body_fat: 1, height: 1, cholesterol: 1, diastolic: 1, duration: 1, fasting: false, glucose: 1, hba1c: 1, hdl: 1, ldl: 1,
        sistolic: 1, tobacco_use: false, triglycerides: 1, waist: 1, weight: 1
      };
      service.saveBiometric(user).subscribe((next) => {
        expect(next).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}biometrics`).flush({
        meta: {
          errCode: 0,
          msg: ''
        },
        response: []

      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected saveBiometric error`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      const user: IUserInfo = {
        body_fat: 1, height: 1, cholesterol: 1, diastolic: 1, duration: 1, fasting: false, glucose: 1, hba1c: 1, hdl: 1, ldl: 1,
        sistolic: 1, tobacco_use: false, triglycerides: 1, waist: 1, weight: 1
      };
      service.saveBiometric(user).subscribe((next) => {
        expect(next).toBeTruthy();
      }, err => {
        expect(err).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}biometrics`).flush({
        meta: {
          errCode: -1,
          msg: 'error'
        },
        response: []

      }, { status: 200, statusText: 'Ok' });
    })));

  it(`should send an expected upload request`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      const user: IUserInfo = {
        body_fat: 1, height: 1, cholesterol: 1, diastolic: 1, duration: 1, fasting: false, glucose: 1, hba1c: 1, hdl: 1, ldl: 1,
        sistolic: 1, tobacco_use: false, triglycerides: 1, waist: 1, weight: 1
      };
      service.upload({}).subscribe((next) => {
        expect(next).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}client_assessment`).flush({
        meta: {
          errCode: 0,
          msg: ''
        },
        response: []

      }, { status: 200, statusText: 'Ok' });
    })));


  it(`should send an expected upload error`, async(inject([BiometricService, HttpTestingController],
    (service: BiometricService, backend: HttpTestingController) => {
      const user: IUserInfo = {
        body_fat: 1, height: 1, cholesterol: 1, diastolic: 1, duration: 1, fasting: false, glucose: 1, hba1c: 1, hdl: 1, ldl: 1,
        sistolic: 1, tobacco_use: false, triglycerides: 1, waist: 1, weight: 1
      };
      service.upload({}).subscribe((next) => {
        expect(next).toBeTruthy();
      }, err => {
        expect(err).toBeTruthy();
      });

      backend.expectOne(`${environment.apiUrl}client_assessment`).flush({
        meta: {
          errCode: -1,
          msg: 'error'
        },
        response: []

      }, { status: 200, statusText: 'Ok' });
    })));




});




