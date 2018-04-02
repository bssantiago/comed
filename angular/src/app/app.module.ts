import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { SharedModule } from './shared/shared.module';
import { BiometricsModule } from './biometrics/biometrics.module';
import { ReportsModule } from './reports/reports.module';

import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';

import { AppRouting } from './app.routing';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ForbiddenComponent
  ],
  imports: [
    BrowserModule,
    SharedModule,
    BiometricsModule,
    ReportsModule,
    AppRouting
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
