import { Component } from '@angular/core';
import {NgIf, NgOptimizedImage} from '@angular/common';
import {Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  standalone: true,
  imports: [
    NgOptimizedImage,
    RouterLink,
    NgIf
  ],
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  isLoggedIn = false;

  constructor(private router: Router) {}

  login() {
    this.isLoggedIn = true;
  }

  goToProfile() {
    this.router.navigate(['/profile']);
  }
}
