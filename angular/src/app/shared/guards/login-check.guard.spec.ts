import { TestBed, async, inject } from '@angular/core/testing';

import { LoginCheckGuard } from './login-check.guard';
import { CookieService } from 'ngx-cookie-service';
import { RouterTestingModule } from '@angular/router/testing';

describe('LoginCheckGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      providers: [LoginCheckGuard, CookieService]
    });
  });

  it('should ...', inject([LoginCheckGuard], (guard: LoginCheckGuard) => {
    expect(guard).toBeTruthy();
  }));
});
