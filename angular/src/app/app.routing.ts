import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'forbidden', component: ForbiddenComponent },
];

export const AppRouting = RouterModule.forRoot(routes, { useHash: true });
