import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BiometricFileModalComponent } from './biometric-file-modal.component';

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../../shared/shared.module';

import { BiometricHomeComponent } from '../../components/biometric-home/biometric-home.component';
import { BiometricMainComponent } from '../../components/biometric-main/biometric-main.component';
import { BiometricSearchComponent } from '../../components/biometric-search/biometric-search.component';
import { BiometricsRouting } from '../../biometrics.routing';
import { BiometricService } from '../../services/biometric.service';
import { BiometricFileComponent } from '../../components/biometric-file/biometric-file.component';
import { Ng2CompleterModule } from 'ng2-completer';

import { RouterTestingModule } from '@angular/router/testing';
import { BiometricHealthLetterComponent } from '../biometric-health-letter/biometric-health-letter.component';

describe('BiometricFileModalComponent', () => {
  let component: BiometricFileModalComponent;
  let fixture: ComponentFixture<BiometricFileModalComponent>;

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
        BiometricHealthLetterComponent,
        BiometricFileModalComponent],
      providers: [BiometricService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricFileModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
