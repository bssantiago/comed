import { TestBed, inject } from '@angular/core/testing';

import { BiometricService } from './biometric.service';
import { ToastrModule } from 'ngx-toastr';
import { SharedModule } from '../../shared/shared.module';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';

describe('BiometricService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        SharedModule,
        ToastrModule.forRoot(),
      ],
      providers: [BiometricService]
    });
  });

  it('should be created', inject([BiometricService], (service: BiometricService) => {
    expect(service).toBeTruthy();
  }));
});
