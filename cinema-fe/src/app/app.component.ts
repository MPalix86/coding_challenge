import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, LoginComponent, TopBarComponent],

  templateUrl: './app.component.html',
})
export class AppComponent {
  constructor(private router: Router) {}

  shouldShowSidebar(): boolean {
    // Verifica se la rotta corrente non è 'login'
    return this.router.url !== '/login';
  }

  shouldShowTopBar(): boolean {
    // Verifica se la rotta corrente non è 'login'
    return this.router.url !== '/login';
  }
}
