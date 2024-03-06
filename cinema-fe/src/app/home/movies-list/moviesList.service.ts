import { Injectable } from '@angular/core';
import { ApiRoutesService } from '../../shared-services/apiRoutes.service';
import { AuthService } from '../../shared-services/auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ShowTime } from '../../types/cinemaTypes';

@Injectable({
  providedIn: 'root',
})
export class MoviesServiceService {
  constructor(
    private auth: AuthService,
    private http: HttpClient,
    private apiRoutes: ApiRoutesService
  ) {}

  getFilterSchedules(startDate: Date): Observable<any> {
    const user = this.auth.verifyLogin();

    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(user.username + ':' + user.password),
    });
    const options = { headers: headers };
    return this.http.get(
      `${
        this.apiRoutes.SCHEDULES_FILTERDATE
      }?startDate=${startDate.toString()}`,
      options
    );
  }

  getCurrent(): Observable<any> {
    const user = this.auth.verifyLogin();

    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(user.username + ':' + user.password),
    });
    const options = { headers: headers };
    return this.http.get(`${this.apiRoutes.SCHEDULES_CURRENT}`, options);
  }

  getShowTimeById(id: number) {
    const user = this.auth.verifyLogin();

    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(user.username + ':' + user.password),
    });
    const options = { headers: headers };
    return this.http.get<ShowTime>(`${this.apiRoutes.SHOWTIME}/${id}`, options);
  }
}
