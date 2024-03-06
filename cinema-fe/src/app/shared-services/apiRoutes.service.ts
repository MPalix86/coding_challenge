// routes.service.ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ApiRoutesService {
  private readonly BASE_URL = 'https://localhost:9080'; // Modifica questo URL con l'URL effettivo del tuo backend
  private readonly API_V1 = '/api/v1';

  public readonly GET_ALL = `/getAll`;

  // Definizione delle rotte
  public readonly LOGIN = `${this.BASE_URL}/login`;
  public readonly THEATERS = `${this.BASE_URL + this.API_V1}/theaters`;

  public readonly SCHEDULES = `${this.BASE_URL + this.API_V1}/schedules`;

  public readonly SCHEDULES_GET_ALL = this.SCHEDULES + this.GET_ALL;
  public readonly SCHEDULES_CURRENT = this.SCHEDULES + '/current';
  public readonly SCHEDULES_FILTERDATE = this.SCHEDULES + '/filterByDate';

  public readonly SHOWTIME = `${this.BASE_URL + this.API_V1}/showTime`;

  constructor() {}
}
