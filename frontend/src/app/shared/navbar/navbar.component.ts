import { Component } from '@angular/core';
import {NgIf, NgOptimizedImage} from '@angular/common';
import {Router, RouterLink} from '@angular/router';
import {AppKeycloakService} from '../../keycloak.service';

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
  constructor(private router: Router, private keycloakService: AppKeycloakService) {}

  isLoggedIn: boolean | undefined

  ngOnInit() {
    this.isLoggedIn = this.keycloakService.isAuthenticated()
  }

  logout() {
    this.keycloakService.logout();
  }

  goToProfile() {
    this.router.navigate(['/profile']);
  }
}
