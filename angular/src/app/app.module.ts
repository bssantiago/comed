import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { SharedModule } from './shared/shared.module';
import { BiometricsModule } from './biometrics/biometrics.module';
import { ReportsModule } from './reports/reports.module';

import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';

import { AppRouting } from './app.routing';
import { BlockUIModule } from 'ng-block-ui';
import { AuthenticateComponent } from './components/authenticate/authenticate.component';
import { BiometricHealthletterComponent } from './biometric/biometric-healthletter/biometric-healthletter.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ForbiddenComponent,
    AuthenticateComponent,
    BiometricHealthletterComponent
  ],
  imports: [
    BlockUIModule.forRoot(),
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
