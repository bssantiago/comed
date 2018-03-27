import { Routes, RouterModule } from '@angular/router';
import { TrackRecordComponent } from './components/track-record/track-record.component';
import { HomeReportsComponent } from './components/home-reports/home-reports.component';

const routes: Routes = [
  {
    path: 'reports',
    component: HomeReportsComponent,
    children: [
      {
        path: 'track',
        component: TrackRecordComponent,
        outlet: 'reports'
      }
    ]
  }
];

export const ReportsRouting = RouterModule.forChild(routes);
