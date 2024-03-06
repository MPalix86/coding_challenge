import { Component } from '@angular/core';
import { AuthService } from '../shared-services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],

  templateUrl: './login.component.html',
})
export class LoginComponent {
  username = '';
  password = '';

  constructor(private authService: AuthService, private router: Router) {}
  dinamicClasses = {
    'border-danger': false,
  };

  async login(event: Event) {
    event.preventDefault();
    const isLogged = await this.authService.login(this.username, this.password);
    if (isLogged) {
      console.log('islogged');
      this.dinamicClasses['border-danger'] = false;
      this.router.navigate(['/home']);
    } else {
      this.dinamicClasses['border-danger'] = true;
    }
  }
}
