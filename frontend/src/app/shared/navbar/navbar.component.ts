import {Component, OnInit} from '@angular/core';
import {NgIf, NgOptimizedImage} from '@angular/common';
import {Router, RouterLink} from '@angular/router';
import {AppKeycloakService} from '../../keycloak.service';
import {RequestService} from '../../service/request.service';

interface Profile {
  id: number;
  name: string;
  icon: string;
}

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
export class NavbarComponent implements OnInit {
  constructor(private router: Router, private keycloakService: AppKeycloakService, private requestService: RequestService) {}
  profile: any;
  isLoggedIn: boolean | undefined
  private profileUrl = 'api/v1/profile';
  ngOnInit() {
    this.isLoggedIn = this.keycloakService.isAuthenticated()
    this.loadProfile();
  }

  logout() {
    this.keycloakService.logout();
  }

  goToProfile() {
    this.router.navigate(['/profile']);
  }

  private loadProfile(): void {
    this.requestService.getInfo(this.profileUrl + "/username").subscribe(
      (response) => {
        this.profile = response;
      },
      (error) => {
        console.error('Ошибка при загрузке профиля', error);
      }
    );
  }
}
