import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackRecordComponent } from './track-record.component';
import { NvD3Module } from 'ng2-nvd3';
import { ChartItemComponent } from '../chart-item/chart-item.component';
import { ReportService } from '../../services/report.service';
import { HttpClientModule } from '@angular/common/http';

describe('TrackRecordComponent', () => {
  let component: TrackRecordComponent;
  let fixture: ComponentFixture<TrackRecordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [NvD3Module, HttpClientModule
      ],
      providers: [ReportService],
      declarations: [TrackRecordComponent, ChartItemComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrackRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
