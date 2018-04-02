import { Routes, RouterModule } from '@angular/router';
import { BiometricHomeComponent } from './components/biometric-home/biometric-home.component';
import { BiometricMainComponent } from './components/biometric-main/biometric-main.component';
import { BiometricSearchComponent } from './components/biometric-search/biometric-search.component';

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
      }
    ]
  }
];

export const BiometricsRouting = RouterModule.forChild(routes);
