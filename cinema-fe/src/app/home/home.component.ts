import { Component, OnInit } from '@angular/core';
import { TopBarComponent } from '../top-bar/top-bar.component';
import { AuthService } from '../shared-services/auth.service';
import { InternalRouteService } from '../shared-services/internalRoute.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [TopBarComponent],
  providers: [InternalRouteService],
  templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {
  user: any;

  constructor(
    private internalRoute: InternalRouteService,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    this.user = this.auth.verifyLogin();
  }

  onLogout(event: Event) {
    console.log('click');
    event.preventDefault;
    this.auth.logout();
  }

  onMoviesClick() {
    this.internalRoute.toMovies();
  }
}
