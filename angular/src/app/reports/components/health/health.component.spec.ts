import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthComponent } from './health.component';
import { ReportBarComponent } from '../report-bar/report-bar.component';
import { HistoryComponent } from '../history/history.component';
import { NvD3Module } from 'ng2-nvd3';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { ReportService } from '../../services/report.service';
import { ReportServiceMock } from '../../../test/mocks/ReportServiceMock';

describe('HealthComponent', () => {
  let component: HealthComponent;
  let fixture: ComponentFixture<HealthComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [NvD3Module, HttpClientModule, RouterTestingModule],
      providers: [{ provide: ReportService, useClass: ReportServiceMock }],
      declarations: [HealthComponent, ReportBarComponent, HistoryComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    component.pid = 1;
    expect(component).toBeTruthy();
  });
});
