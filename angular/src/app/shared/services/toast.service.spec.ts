import { TestBed, inject } from '@angular/core/testing';

import { ToastService } from './toast.service';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from '../shared.module';
import { ToastrModule } from 'ngx-toastr';

describe('ToastService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        SharedModule,
        ToastrModule.forRoot(),
      ],
      providers: [ToastService]
    });
  });

  it('should be created', inject([ToastService], (service: ToastService) => {
    expect(service).toBeTruthy();
  }));
});
