import { Routes, RouterModule } from '@angular/router';
import { BiometricHomeComponent } from './components/biometric-home/biometric-home.component';
import { BiometricMainComponent } from './components/biometric-main/biometric-main.component';

const routes: Routes = [
  {
    path: 'biometrics',
    component: BiometricHomeComponent,
    children: [
      {
        path: 'user/:id',
        component: BiometricMainComponent,
        outlet: 'biometrics'
      }
    ]
  }
];

export const BiometricsRouting = RouterModule.forChild(routes);
