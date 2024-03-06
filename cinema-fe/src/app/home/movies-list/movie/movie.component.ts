import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MoviesServiceService } from '../moviesList.service';
import { AuthService } from '../../../shared-services/auth.service';
import { ShowTime } from '../../../types/cinemaTypes';
import { CommonModule } from '@angular/common';
import { InternalRouteService } from '../../../shared-services/internalRoute.service';

@Component({
  selector: 'app-movie',
  standalone: true,
  imports: [CommonModule],

  templateUrl: './movie.component.html',
  styleUrl: './movie.component.css',
})
export class MovieComponent implements OnInit {
  user: any;
  showTime!: ShowTime;
  isLoading = false;

  constructor(
    private auth: AuthService,
    private mlService: MoviesServiceService,
    private route: ActivatedRoute,
    private internalRoute: InternalRouteService
  ) {}

  ngOnInit(): void {
    this.user = this.auth.verifyLogin();
    const showTimeId = this.route.snapshot.params['showTimeId'];

    this.mlService.getShowTimeById(showTimeId).subscribe({
      next: (data) => {
        console.log(data);
        this.isLoading = true;
        this.showTime = data;
      },
      error: (err) => {
        console.error(err);
        alert('errore qualcosa è andato storto');
      },
      complete: () => {
        this.isLoading = false;
      },
    });
  }

  getSeatsColor(num: number, totalSeats: number): string {
    if (num - totalSeats == 0) return '#800000';
    else if (num > totalSeats / 2) return '#e59a17';
    else return 'green';
  }

  getFreeSeats() {
    const capacity = this.showTime?.schedule?.theater.capacity ?? 0;
    const seats = capacity - this.showTime.occupiedSeats;
    return seats;
  }

  goBack() {
    console.log('click');
    this.internalRoute.toMovies();
  }
}
