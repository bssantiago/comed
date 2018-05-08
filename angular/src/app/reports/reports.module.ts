import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NvD3Module } from 'ng2-nvd3';

import {FormsModule} from '@angular/forms';

import 'd3';
import 'nvd3';


import { ChartItemComponent } from './components/chart-item/chart-item.component';
import { HomeReportsComponent } from './components/home-reports/home-reports.component';

import { ReportsRouting } from './reports.routing';
import { TrackRecordComponent } from './components/track-record/track-record.component';

import { ReportService } from './services/report.service';
import { HealthComponent } from './components/health/health.component';
import { ReportBarComponent } from './components/report-bar/report-bar.component';


@NgModule({
  imports: [
    FormsModule,
    NvD3Module,
    CommonModule,
    ReportsRouting
  ],
  declarations: [ChartItemComponent, HomeReportsComponent, TrackRecordComponent, HealthComponent, ReportBarComponent],
  providers: [ReportService],
  exports: [ChartItemComponent]
})
export class ReportsModule { }
