import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { finalize } from 'rxjs/operators';
import { AuthService } from './api/security/service/auth.service';
import { JwtResponse } from './api/security/payload/response/jwtResponse.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less'],
})
export class AppComponent {
  currentUser: JwtResponse;

  constructor(private router: Router, private authService: AuthService) {
    this.authService.currentUser.subscribe((x) => (this.currentUser = x));
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
