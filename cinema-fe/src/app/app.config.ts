import {
  ApplicationConfig,
  Component,
  importProvidersFrom,
} from '@angular/core';
import { provideRouter } from '@angular/router';
import {
  HttpClientModule,
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';
import { apiInterceptor } from './interceptors/api.interceptor';
import { Err404Component } from './err404/err404.component';
import { LoginComponent } from './login/login.component';

const ROUTES = [
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  {
    path: 'home',
    loadChildren: () => import('./home/home.routes').then((r) => r.HOME_ROUTES),
  },
  { path: '**', component: Err404Component },
];

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(ROUTES),
    importProvidersFrom(HttpClientModule),
    provideHttpClient(withInterceptors([apiInterceptor])),
  ],
};
