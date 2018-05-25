import { Routes, RouterModule } from '@angular/router';
import { BiometricHomeComponent } from './components/biometric-home/biometric-home.component';
import { BiometricMainComponent } from './components/biometric-main/biometric-main.component';
import { BiometricSearchComponent } from './components/biometric-search/biometric-search.component';
import { BiometricFileComponent } from './components/biometric-file/biometric-file.component';
import { BiometricHealthLetterComponent } from './components/biometric-health-letter/biometric-health-letter.component';
import { LoginCheckGuard } from '../shared/guards/login-check.guard';

const routes: Routes = [
  {
    path: 'biometrics',
    component: BiometricHomeComponent,
    children: [
      { path: '', redirectTo: '/not-found', pathMatch: 'full' },
      {
        path: 'user/:id',
        canActivate: [LoginCheckGuard],
        component: BiometricMainComponent,
      },
      {
        path: 'healthletter/:id',
        canActivate: [LoginCheckGuard],
        component: BiometricHealthLetterComponent,
      },
      {
        path: 'search',
        canActivate: [LoginCheckGuard],
        component: BiometricSearchComponent,
      },
      {
        path: 'search/:clientId',
        canActivate: [LoginCheckGuard],
        component: BiometricSearchComponent,
      },
      {
        path: 'search/:clientId/:koordinatorId',
        canActivate: [LoginCheckGuard],
        component: BiometricSearchComponent,
      },
      {
        path: 'search/:clientId/:koordinatorId/:first_name/:last_name/:date_of_birth/:gender',
        canActivate: [LoginCheckGuard],
        component: BiometricSearchComponent,
      },
      {
        path: 'upload',
        canActivate: [LoginCheckGuard],
        component: BiometricFileComponent,
      }
    ]
  }
];

export const BiometricsRouting = RouterModule.forChild(routes);
