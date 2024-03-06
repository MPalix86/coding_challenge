import { Component, OnInit } from '@angular/core';
import { AuthService } from '../shared-services/auth.service';
import { InternalRouteService } from '../shared-services/internalRoute.service';

@Component({
  selector: 'app-top-bar',
  standalone: true,
  imports: [],
  templateUrl: './top-bar.component.html',
})
export class TopBarComponent implements OnInit {
  user: any;

  constructor(
    private internalRoute: InternalRouteService,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    this.user = this.auth.verifyLogin();
  }

  onClickUser(event: Event) {
    event.preventDefault();
    this.internalRoute.toHome();
  }
  onClickHome(event: Event) {
    event.preventDefault();
    this.internalRoute.toHome();
  }
}
