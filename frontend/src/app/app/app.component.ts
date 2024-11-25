import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {KeycloakAngularModule} from 'keycloak-angular';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {AppKeycloakService} from '../keycloak.service';

@Component({
  selector: 'app-app',
  standalone: true,
  imports: [
    RouterOutlet,
    KeycloakAngularModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  providers: [
    AppKeycloakService,
  ]
})
export class AppComponent {
  constructor(private keycloakService: AppKeycloakService) {
    this.initKeycloak();
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
