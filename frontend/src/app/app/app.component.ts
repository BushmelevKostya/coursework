import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {KeycloakAngularModule} from 'keycloak-angular';
import {AppKeycloakService} from '../keycloak.service';
import {RequestService} from '../service/request.service';
import {HttpClientModule} from '@angular/common/http';

@Component({
  selector: 'app-app',
  standalone: true,
  imports: [
    RouterOutlet,
    KeycloakAngularModule,
    HttpClientModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  providers: [
    AppKeycloakService,
    RequestService
  ]
})
export class AppComponent {
  constructor(private keycloakService: AppKeycloakService, requestService: RequestService) {
    this.initKeycloak();

    requestService.sendUsersToBackend();

    const email = 'example@example.com';
    requestService.getUserByEmail(email).subscribe(
      (user) => {
        console.log('Найден пользователь', user);
      },
      (error) => {
        console.error('Ошибка при получении пользователя', error);
      }
    );
  }

  private async initKeycloak() {
    try {
      const isInitialized = await this.keycloakService.initKeycloak();
      if (isInitialized) {
        console.log('Keycloak initialized successfully');
      } else {
        console.error('Keycloak initialization failed');
      }
    } catch (error) {
      console.error('Error during Keycloak initialization', error);
    }
  }
}
