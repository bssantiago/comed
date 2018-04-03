import { TestBed, inject } from '@angular/core/testing';

import { BiometricService } from './biometric.service';

describe('BiometricService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BiometricService]
    });
  });

  it('should be created', inject([BiometricService], (service: BiometricService) => {
    expect(service).toBeTruthy();
  }));
});
