import { Routes } from '@angular/router';
import { MoviesListComponent } from './movies-list.component';

export const MOVIES_ROUTES: Routes = [
  {
    path: '',
    component: MoviesListComponent,
  },
  {
    path: 'movie/:showTimeId',
    loadComponent: () =>
      import('./movie/movie.component').then((m) => m.MovieComponent),
  },
];
