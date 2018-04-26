import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NvD3Module } from 'ng2-nvd3';

import 'd3';
import 'nvd3';


import { ChartItemComponent } from './components/chart-item/chart-item.component';
import { HomeReportsComponent } from './components/home-reports/home-reports.component';

import { ReportsRouting } from './reports.routing';
import { TrackRecordComponent } from './components/track-record/track-record.component';

import { ReportService } from './services/report.service';

@NgModule({
  imports: [
    NvD3Module,
    CommonModule,
    ReportsRouting
  ],
  declarations: [ChartItemComponent, HomeReportsComponent, TrackRecordComponent],
  providers: [ReportService],
  exports: [ChartItemComponent]
})
export class ReportsModule { }
