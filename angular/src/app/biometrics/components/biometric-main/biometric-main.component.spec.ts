import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BiometricMainComponent } from './biometric-main.component';

import { FormsModule } from '@angular/forms';
import {
  RouterTestingModule
} from '@angular/router/testing';
import { BiometricService } from '../../services/biometric.service';
import { HttpClientModule } from '@angular/common/http';
import { ToastrService, ToastrModule } from 'ngx-toastr';
import { SharedModule } from '../../../shared/shared.module';
import { BiometricsServiceMock } from '../../../test/mocks/biometricServiceMock';
import { ToastService } from '../../../shared/services/toast.service';

describe('BiometricMainComponent', () => {
  let component: BiometricMainComponent;
  let fixture: ComponentFixture<BiometricMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule,
        RouterTestingModule,
        HttpClientModule,
        SharedModule,
        ToastrModule.forRoot()],
      declarations: [BiometricMainComponent],
      providers: [
        { provide: BiometricService, useClass: BiometricsServiceMock },
        ToastService
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open doctorLetter', () => {
    const windowOpenSpy = spyOn(window, 'open');
    component.existBiometrics = true;
    component.participantId = 1;
    component.docLetter();
    expect(windowOpenSpy).toHaveBeenCalledWith('/comed/#/biometrics/healthletter/1', 'Doctor Letter', 'width=600,height=768');
  });

  it('should fail doctorLetter', () => {
    const toastService: ToastService = fixture.debugElement.injector.get(ToastService);
    const toastSpy = spyOn(toastService, 'error');
    component.existBiometrics = false;
    component.docLetter();
    expect(toastSpy).toHaveBeenCalled();
  });

  it('should open healthLetter', () => {
    const windowOpenSpy = spyOn(window, 'open');
    component.existBiometrics = true;
    component.participantId = 1;
    component.healthLetter();
    expect(windowOpenSpy).toHaveBeenCalledWith('/comed/#/reports/health/1', 'Doctor Letter', 'width=600,height=768');
  });

  it('should switchEntries isNewBiometrics', () => {
    component.user = {
      body_fat: 0, height: 0, cholesterol: 0, diastolic: 0, duration: 0, fasting: false, glucose: 0, hba1c: 0, hdl: 0, ldl: 0,
      sistolic: 0, tobacco_use: false, triglycerides: 0, waist: 0, weight: 0,
    };

    component.lastEntryUser = {
      body_fat: 1, height: 1, cholesterol: 1, diastolic: 1, duration: 1, fasting: false, glucose: 1, hba1c: 1, hdl: 1, ldl: 1,
      sistolic: 1, tobacco_use: false, triglycerides: 1, waist: 1, weight: 1,
    };

    component.isNewBiometrics = true;
    component.switchEntries();
    expect(component.user.height).toBe(component.lastEntryUser.height);
    expect(component.user.body_fat).toBe(component.lastEntryUser.body_fat);
    expect(component.user.cholesterol).toBe(component.lastEntryUser.cholesterol);
    expect(component.user.diastolic).toBe(component.lastEntryUser.diastolic);
    expect(component.user.duration).toBe(component.lastEntryUser.duration);
    expect(component.user.fasting).toBe(component.lastEntryUser.fasting);
    expect(component.user.glucose).toBe(component.lastEntryUser.glucose);
    expect(component.user.hba1c).toBe(component.lastEntryUser.hba1c);
    expect(component.user.hdl).toBe(component.lastEntryUser.hdl);
    expect(component.user.ldl).toBe(component.lastEntryUser.ldl);
    expect(component.user.sistolic).toBe(component.lastEntryUser.sistolic);
    expect(component.user.tobacco_use).toBe(component.lastEntryUser.tobacco_use);
    expect(component.user.triglycerides).toBe(component.lastEntryUser.triglycerides);
    expect(component.user.waist).toBe(component.lastEntryUser.waist);
    expect(component.user.weight).toBe(component.lastEntryUser.weight);

  });

  it('should switchEntries !isNewBiometrics', () => {
    component.user = {
      body_fat: 0, height: 0, cholesterol: 0, diastolic: 0, duration: 0, fasting: false, glucose: 0, hba1c: 0, hdl: 0, ldl: 0,
      sistolic: 0, tobacco_use: false, triglycerides: 0, waist: 0, weight: 0,
    };

    component.newEntryUser = {
      body_fat: 1, height: 1, cholesterol: 1, diastolic: 1, duration: 1, fasting: false, glucose: 1, hba1c: 1, hdl: 1, ldl: 1,
      sistolic: 1, tobacco_use: false, triglycerides: 1, waist: 1, weight: 1,
    };

    component.isNewBiometrics = false;
    component.switchEntries();
    expect(component.user.height).toBe(component.newEntryUser.height);
    expect(component.user.body_fat).toBe(component.newEntryUser.body_fat);
    expect(component.user.cholesterol).toBe(component.newEntryUser.cholesterol);
    expect(component.user.diastolic).toBe(component.newEntryUser.diastolic);
    expect(component.user.duration).toBe(component.newEntryUser.duration);
    expect(component.user.fasting).toBe(component.newEntryUser.fasting);
    expect(component.user.glucose).toBe(component.newEntryUser.glucose);
    expect(component.user.hba1c).toBe(component.newEntryUser.hba1c);
    expect(component.user.hdl).toBe(component.newEntryUser.hdl);
    expect(component.user.ldl).toBe(component.newEntryUser.ldl);
    expect(component.user.sistolic).toBe(component.newEntryUser.sistolic);
    expect(component.user.tobacco_use).toBe(component.newEntryUser.tobacco_use);
    expect(component.user.triglycerides).toBe(component.newEntryUser.triglycerides);
    expect(component.user.waist).toBe(component.newEntryUser.waist);
    expect(component.user.weight).toBe(component.newEntryUser.weight);
  });

  it('should not save data', () => {
    const biometric: BiometricService = fixture.debugElement.injector.get(BiometricService);
    const biometricSpy = spyOn(biometric, 'saveBiometric');
    const user = {
      body_fat: 0, height: 0, cholesterol: 0, diastolic: 0, duration: 0, fasting: false, glucose: 0, hba1c: 0, hdl: 0, ldl: 0,
      sistolic: 0, tobacco_use: false, triglycerides: 0, waist: 0, weight: 0,
    };
    component.save(user, false);
    expect(biometricSpy).toHaveBeenCalledTimes(0);
  });

  it('should save data', () => {
    const toastService: ToastService = fixture.debugElement.injector.get(ToastService);
    const toastSpy = spyOn(toastService, 'success');
    const user = {
      body_fat: 0, height: 0, cholesterol: 0, diastolic: 0, duration: 0, fasting: false, glucose: 0, hba1c: 0, hdl: 0, ldl: 0,
      sistolic: 0, tobacco_use: false, triglycerides: 0, waist: 0, weight: 0,
    };
    component.isNewBiometrics = true;
    component.participantId = 1;
    component.seconds = 20;
    component.save(user, true);
    expect(toastSpy).toHaveBeenCalledTimes(1);
    expect(toastSpy).toHaveBeenCalledWith('New biometric created', 'Success');
  });

  it('should modify save data', () => {
    const toastService: ToastService = fixture.debugElement.injector.get(ToastService);
    const toastSpy = spyOn(toastService, 'success');
    const user = {
      body_fat: 0, height: 0, cholesterol: 0, diastolic: 0, duration: 0, fasting: false, glucose: 0, hba1c: 0, hdl: 0, ldl: 0,
      sistolic: 0, tobacco_use: false, triglycerides: 0, waist: 0, weight: 0,
    };
    component.isNewBiometrics = false;
    component.participantId = 1;
    component.seconds = 20;
    component.save(user, true);
    expect(toastSpy).toHaveBeenCalledTimes(1);
    expect(toastSpy).toHaveBeenCalledWith('Biometric modificated', 'Success');
  });

  /*
   public save(model: IUserInfo, isValid: boolean): void {
    if (isValid) {
      model.participant_id = this.participantId;
      model.duration = this.seconds;
      if (this.isNewBiometrics) {
        this.bservice.saveBiometric(model)
          .subscribe((data: IGenericResponse<any>) => {
            this.toast.success('New biometric created', 'Success');
            this.loadUserData();
          });
      } else {
        model.biometric_id = this.user.biometric_id;
        this.bservice.update(model)
          .subscribe((data: IGenericResponse<any>) => {
            this.toast.success('Biometric modificated', 'Success');
            this.loadUserData();
            this.switchEntries();
          });
      }
    }
  }
  */

});
