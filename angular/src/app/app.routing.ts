import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';
import { AuthenticateComponent } from './components/authenticate/authenticate.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'forbidden', component: ForbiddenComponent },
  { path: 'forbidden/:client', component: ForbiddenComponent },
  { path: 'authenticate/:client/:patient', component: AuthenticateComponent},
  { path: 'authenticate/:client', component: AuthenticateComponent},
  { path: 'authenticate', component: AuthenticateComponent}
];

export const AppRouting = RouterModule.forRoot(routes, { useHash: true });
