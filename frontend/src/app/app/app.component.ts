import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {keycloakConfig} from '../keycloak.config';
import {KeycloakService, KeycloakAngularModule} from 'keycloak-angular';

@Component({
  selector: 'app-app',
  standalone: true,
  imports: [
    RouterOutlet,
    KeycloakAngularModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  providers: [KeycloakService]
})
export class AppComponent {
  constructor(private keycloakService: KeycloakService) {
    this.initKeycloak();
  }

  private initKeycloak() {
    try {
      this.keycloakService.init({
        config: keycloakConfig,
        initOptions: {
          onLoad: 'login-required',
          checkLoginIframe: false,
          redirectUri: 'http://localhost:4200/home',
        },
        loadUserProfileAtStartUp: true
      });
    } catch (error) {
      console.error("Error during Keycloak initialization", error);
    }
  }
}
