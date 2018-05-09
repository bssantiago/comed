import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BiometricHealthLetterComponent } from './biometric-health-letter.component';
import { BiometricService } from '../../services/biometric.service';
import { RouterTestingModule } from '@angular/router/testing';
import { Ng2CompleterModule } from 'ng2-completer';
import { SharedModule } from '../../../shared/shared.module';
import { FormsModule } from '@angular/forms';
import { BiometricsRouting } from '../../biometrics.routing';
import { CommonModule } from '@angular/common';
import { BiometricFileModalComponent } from '../biometric-file-modal/biometric-file-modal.component';
import { BiometricFileComponent } from '../biometric-file/biometric-file.component';
import { BiometricSearchComponent } from '../biometric-search/biometric-search.component';
import { BiometricMainComponent } from '../biometric-main/biometric-main.component';
import { BiometricHomeComponent } from '../biometric-home/biometric-home.component';

describe('BiometricHealthLetterComponent', () => {
  let component: BiometricHealthLetterComponent;
  let fixture: ComponentFixture<BiometricHealthLetterComponent>;

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
        BiometricFileModalComponent
      ],
      providers: [BiometricService],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricHealthLetterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
