import { LoginPage } from './pages/login-page/login-page';
import { Routes } from '@angular/router';
import { MainPage } from './pages/main-page/main-page';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
  { path: 'main', component: MainPage, canActivate: [authGuard] },
  { path: 'login', component: LoginPage },
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];
