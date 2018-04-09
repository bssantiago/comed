import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';

import { BiometricHomeComponent } from './components/biometric-home/biometric-home.component';
import { BiometricMainComponent } from './components/biometric-main/biometric-main.component';
import { BiometricSearchComponent } from './components/biometric-search/biometric-search.component';
import { BiometricFileComponent } from './components/biometric-file/biometric-file.component';
import { BiometricsRouting } from './biometrics.routing';
import { BiometricService } from './services/biometric.service';
import { BiometricFileModalComponent } from './components/biometric-file-modal/biometric-file-modal.component';



@NgModule({
  imports: [
    CommonModule,
    BiometricsRouting,
    FormsModule,
    SharedModule
  ],
  declarations: [BiometricHomeComponent, BiometricMainComponent, BiometricSearchComponent, BiometricFileComponent, BiometricFileModalComponent],
  providers: [BiometricService]
})
export class BiometricsModule { }
