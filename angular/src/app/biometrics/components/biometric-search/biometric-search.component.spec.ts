import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BiometricSearchComponent } from './biometric-search.component';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from '../../../shared/shared.module';
import { ToastrModule } from 'ngx-toastr';
import { Ng2CompleterModule, CompleterService } from 'ng2-completer';
import { BiometricService } from '../../services/biometric.service';
import { ToastService } from '../../../shared/services/toast.service';
import { LocalStorageService } from '../../../shared/services/local-storage.service';
import { IParticipantSearch } from '../../../shared/interfaces/participant-info';
import { BiometricsServiceMock } from '../../../test/mocks/biometricServiceMock';
import { Router } from '@angular/router';

describe('BiometricSearchComponent', () => {
  let component: BiometricSearchComponent;
  let fixture: ComponentFixture<BiometricSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule,
        RouterTestingModule,
        HttpClientModule,
        SharedModule,
        ToastrModule.forRoot(),
        Ng2CompleterModule],
      providers: [
        { provide: BiometricService, useClass: BiometricsServiceMock }
        , ToastService, CompleterService, LocalStorageService],
      declarations: [BiometricSearchComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /*
    it('should create', () => {
      expect(component).toBeTruthy();
    });
  */

  it('should set Participant', () => {
    const toastService: ToastService = fixture.debugElement.injector.get(ToastService);
    const toastSpy = spyOn(toastService, 'error');
    const user: IParticipantSearch = {
      lastname: '',
      name: ''
    };
    component.setParticipant();
    expect(toastSpy).toHaveBeenCalledTimes(1);
    expect(toastSpy).toHaveBeenCalledWith('client, lastname, firstname, date of birth, and gender are mandatory fields to add new patient'
      , 'Error');
  });

  it('should set Participant', () => {
    const toastService: ToastService = fixture.debugElement.injector.get(ToastService);
    const toastSpy = spyOn(toastService, 'success');

    const routerService: Router = fixture.debugElement.injector.get(Router);
    const routerSpy = spyOn(routerService, 'navigate');


    component.clientItem = { id: 3, program: 'p3' };
    component.koordinatorId = 3;
    const user: IParticipantSearch = { lastname: 'a', name: 'a', dob: new Date(), gender: 'M' };
    component.user = user;
    component.setParticipant();
    expect(toastSpy).toHaveBeenCalledTimes(1);
    expect(toastSpy).toHaveBeenCalledWith('Patient saved', 'Success');
    expect(routerSpy).toHaveBeenCalledTimes(1);
    expect(routerSpy).toHaveBeenCalledWith([`/biometrics/user/true`]);
  });

  it('should search', () => {
    component.clientItem = { id: 3, program: 'p3' };
    component.search(true);
    expect(component.table.data.length).toBe(0);
    expect(component.pages).toBe(3);
  });

  it('should not search', () => {

    const bService: BiometricService = fixture.debugElement.injector.get(BiometricService);
    const bSpy = spyOn(bService, 'search');
    component.clientItem = { id: 3, program: 'p3' };
    component.search(false);
    expect(bSpy).toHaveBeenCalledTimes(0);
  });

});
