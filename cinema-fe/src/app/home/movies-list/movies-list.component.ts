import { Component, OnInit } from '@angular/core';
import { MoviesServiceService } from './moviesList.service';
import { AuthService } from '../../shared-services/auth.service';
import { Schedule, ShowTime } from '../../types/cinemaTypes';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InternalRouteService } from '../../shared-services/internalRoute.service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-movies-list',
  standalone: true,
  imports: [DatePipe, FormsModule, CommonModule],
  templateUrl: './movies-list.component.html',
  styleUrl: './movies-list.css',
})
export class MoviesListComponent implements OnInit {
  user: any;
  schedules: Schedule[] = [];
  filteredDate!: Date;
  isLoading = false;
  calendarClasses = {
    'border-danger': false,
  };

  constructor(
    private auth: AuthService,
    private mlService: MoviesServiceService,
    private internalRoute: InternalRouteService
  ) {}

  ngOnInit(): void {
    this.filteredDate = new Date();
    this.user = this.auth.verifyLogin();
    this.mlService.getCurrent().subscribe({
      next: (data) => this.loadSchedule(data),
      error: (err) => alert('some error occurred'),
    });
  }

  getHourMinutesFromNumber(numebr: number) {
    var hours = Math.floor(numebr / 60);
    var minutes = numebr % 60;
    return hours + ':' + minutes;
  }

  filterMoviesByDate() {
    if (
      new Date(this.filteredDate) < new Date() &&
      this.user.role !== 'ROLE_ADMIN'
    ) {
      console.log('non sei autorizzato');
      alert('non sei autorizzato a vedere la programmazione passata');
      return;
    }

    if (typeof this.filteredDate !== 'undefined') {
      console.log(this.filteredDate);
      this.isLoading = true;
      this.mlService.getFilterSchedules(this.filteredDate).subscribe({
        next: (data) => this.loadSchedule(data),
        error: (err) => console.error('Observable emitted an error: ' + err),
        complete: () => {
          this.isLoading = false;
          this.calendarClasses['border-danger'] = false;
        },
      });
    } else {
      this.calendarClasses['border-danger'] = true;
    }
  }

  getSeatsColor(num: number, totalSeats: number): string {
    if (num - totalSeats == 0) return '#800000';
    else if (num > totalSeats / 2) return '#e59a17';
    else return 'green';
  }

  // mapSchedule(data: Schedule[]) {
  //   console.log('data', data);
  //   this.schedules = data.map((schedules: Schedule) => {
  //     // assing random seats number
  //     const seats = schedules.theater.capacity;
  //     schedules.showTimes.forEach((st, i, arr) => {
  //       arr[i].occupiedSeats = Math.floor(Math.random() * seats) + 1;
  //     });
  //     return schedules;
  //   });
  // }

  loadSchedule(data: Schedule[]) {
    console.log(data);
    this.isLoading = true;
    this.schedules = data;
    this.isLoading = false;
  }

  goToDetails(showTimeId: number): void {
    this.internalRoute.toMovie(showTimeId);
  }
}
