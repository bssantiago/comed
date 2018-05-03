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
      providers: [{provide : BiometricService, useClass: BiometricsServiceMock }]
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
});
