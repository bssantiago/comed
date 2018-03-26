import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { BiometricHomeComponent } from './components/biometric-home/biometric-home.component';
import { BiometricMainComponent } from './components/biometric-main/biometric-main.component';
import { BiometricsRouting } from './biometrics.routing';


@NgModule({
  imports: [
    CommonModule,
    BiometricsRouting,
    FormsModule
  ],
  declarations: [BiometricHomeComponent, BiometricMainComponent]
})
export class BiometricsModule { }
