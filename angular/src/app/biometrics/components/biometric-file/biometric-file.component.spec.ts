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
      providers: [BiometricService]
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
});
