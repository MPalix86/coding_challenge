import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { ApiRoutesService } from './apiRoutes.service';
import { InternalRouteService } from './internalRoute.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly STORAGE_KEY = 'user';

  constructor(
    private internalRoute: InternalRouteService,
    private apiRoutes: ApiRoutesService,
    private http: HttpClient
  ) {}

  async login(username: string, password: string): Promise<boolean> {
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(username + ':' + password),
    });

    try {
      const authObj = await this.http
        .get<any>(this.apiRoutes.LOGIN, { headers })
        .toPromise();

      if (authObj.status) {
        const user = {
          username: username,
          password: password,
          role: authObj.role,
        };
        sessionStorage.setItem(this.STORAGE_KEY, JSON.stringify(user));
        return true;
      } else {
        this.logout();
        return false;
      }
    } catch (error) {
      this.logout();
      console.error(error);
      return false;
    }
  }

  verifyLogin(): any {
    if (!sessionStorage.getItem(this.STORAGE_KEY)) {
      this.internalRoute.toLogin();
    } else {
      const storedValue = sessionStorage.getItem(this.STORAGE_KEY) || '{}';
      const user = JSON.parse(storedValue);
      if (!user) this.internalRoute.toLogin();
      return user;
    }
  }

  logout(): void {
    sessionStorage.removeItem(this.STORAGE_KEY);
    this.internalRoute.toLogin();
  }
}
