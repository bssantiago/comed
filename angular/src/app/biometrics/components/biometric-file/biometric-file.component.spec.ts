import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BiometricFileComponent } from './biometric-file.component';

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../../shared/shared.module';

import { BiometricHomeComponent } from '../../components/biometric-home/biometric-home.component';
import { BiometricMainComponent } from '../../components/biometric-main/biometric-main.component';
import { BiometricSearchComponent } from '../../components/biometric-search/biometric-search.component';
import { BiometricsRouting } from '../../biometrics.routing';
import { BiometricService } from '../../services/biometric.service';
import { BiometricFileModalComponent } from '../../components/biometric-file-modal/biometric-file-modal.component';
import { Ng2CompleterModule } from 'ng2-completer';

import { RouterTestingModule } from '@angular/router/testing';
import { BiometricsServiceMock } from '../../../test/mocks/biometricServiceMock';
import { LocalStorageService } from '../../../shared/services/local-storage.service';
import { LocalStorageServiceMock } from '../../../test/mocks/localStorageServiceMock';
import { IFile } from '../../../shared/interfaces/Ifile';

describe('BiometricFileComponent', () => {
  let component: BiometricFileComponent;
  let fixture: ComponentFixture<BiometricFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        CommonModule,
        BiometricsRouting,
        FormsModule,
        SharedModule,
        Ng2CompleterModule,
        RouterTestingModule
      ],
      declarations: [
        BiometricHomeComponent,
        BiometricMainComponent,
        BiometricSearchComponent,
        BiometricFileComponent,
        BiometricFileModalComponent],
      providers: [
        { provide: BiometricService, useClass: BiometricsServiceMock },
        { provide: LocalStorageService, useClass: LocalStorageServiceMock }
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have at least 2 files and options setted', () => {
    component.ngOnInit();
    expect(component.table.data.length).toBe(2);
    component.localStorageClientId = null;
    component.clientChange('3');
    expect(component.optionsErrors.disabled).toBe(false);
  });

  it('should have disabled client if we have a key in our localstorage', () => {
    component.ngOnInit();
    expect(component.optionsErrors.disabled).toBe(true);
  });

  it('should fail upload if data is not valid', () => {
    const fileForm: IFile = {
      rewardDate: undefined,
      range: undefined,
      data: new File([], 'Mock.zip', { type: 'application/zip' }),
      clientId: '2',
      programId: '2',
    };
    component.upload(fileForm, false);
    expect(component.optionsErrors.fileError).toBe(false);
    expect(component.optionsErrors.rewardDateError).toBe(true);
    expect(component.optionsErrors.rangeError).toBe(true);
  });

  it('should succed upload if data is valid', () => {
    component.table.data = [];
    const fileForm: IFile = {
      rewardDate: new Date(),
      range: {
        start: new Date(),
        end: new Date()
      },
      data: new File([], 'Mock.csv', { type: 'application/zip' }),
      clientId: '2',
      programId: '2',
    };
    component.upload(fileForm, true);
    expect(component.table.data.length).toBe(2);
  });


});
