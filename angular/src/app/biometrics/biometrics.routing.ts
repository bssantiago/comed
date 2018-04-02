import { Routes, RouterModule } from '@angular/router';
import { BiometricHomeComponent } from './components/biometric-home/biometric-home.component';
import { BiometricMainComponent } from './components/biometric-main/biometric-main.component';
import { BiometricSearchComponent } from './components/biometric-search/biometric-search.component';
import { BiometricFileComponent } from './components/biometric-file/biometric-file.component';

const routes: Routes = [
  {
    path: 'biometrics',
    component: BiometricHomeComponent,
    children: [
      {
        path: 'user',
        component: BiometricMainComponent,
      },
      {
        path: 'user/:id',
        component: BiometricMainComponent,
      },
      {
        path: 'search',
        component: BiometricSearchComponent,
      },
      {
        path: 'upload',
        component: BiometricFileComponent,
      }
    ]
  }
];

export const BiometricsRouting = RouterModule.forChild(routes);
