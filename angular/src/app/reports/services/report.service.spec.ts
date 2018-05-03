import { TestBed, inject } from '@angular/core/testing';

import { ReportService } from './report.service';
import { HttpClientModule } from '@angular/common/http';

describe('ReportService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [ReportService]
    });
  });

  it('should be created', inject([ReportService], (service: ReportService) => {
    expect(service).toBeTruthy();
  }));
});
