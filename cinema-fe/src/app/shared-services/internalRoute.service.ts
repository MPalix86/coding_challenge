import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Schedule, ShowTime } from '../types/cinemaTypes';

@Injectable({
  providedIn: 'root',
})
export class InternalRouteService {
  constructor(private router: Router) {}

  toLogin() {
    this.router.navigate(['/login']);
  }

  toHome() {
    this.router.navigate(['/home']);
  }

  toMovies() {
    this.router.navigate(['/home/movies']);
  }

  toMovie(showTimeId: number) {
    console.log('arrivato qui', showTimeId);
    this.router.navigate(['/home/movies/movie', showTimeId]);
  }
}
