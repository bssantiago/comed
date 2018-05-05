import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';
import { AuthenticateComponent } from './components/authenticate/authenticate.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'forbidden', component: ForbiddenComponent },
  { path: 'forbidden/:client', component: ForbiddenComponent },
  { path: 'authenticate/:token/:nonce/:sk/:requestBy/:signature/:client/:patient', component: AuthenticateComponent},
  { path: 'authenticate/:token/:nonce/:sk/:requestBy/:signature/:client', component: AuthenticateComponent},
  { path: 'authenticate/:token/:nonce/:sk/:requestBy/:signature', component: AuthenticateComponent}
];

export const AppRouting = RouterModule.forRoot(routes, { useHash: true });
