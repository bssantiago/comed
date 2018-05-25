import { Routes, RouterModule } from '@angular/router';
import { TrackRecordComponent } from './components/track-record/track-record.component';
import { HomeReportsComponent } from './components/home-reports/home-reports.component';
import { LoginCheckGuard } from '../shared/guards/login-check.guard';
import { HealthComponent } from './components/health/health.component';

const routes: Routes = [
  {
    path: 'reports',
    component: HomeReportsComponent,
    children: [
      { path: '', redirectTo: '/not-found', pathMatch: 'full' },
      {
        path: 'track/:biometricId',
        component: TrackRecordComponent,
        canActivate: [LoginCheckGuard]
      },
      {
        path: 'health/:pid',
        component: HealthComponent,
        canActivate: [LoginCheckGuard]
      }

    ]
  }
];

export const ReportsRouting = RouterModule.forChild(routes);
