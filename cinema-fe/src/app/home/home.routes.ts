import { Routes } from '@angular/router';

import { HomeComponent } from './home.component';
import { MoviesListComponent } from './movies-list/movies-list.component';
import { MovieComponent } from './movies-list/movie/movie.component';

export const HOME_ROUTES: Routes = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'movies',
    loadChildren: () =>
      import('./movies-list/movies-list.routes').then((m) => m.MOVIES_ROUTES),
  },
];
