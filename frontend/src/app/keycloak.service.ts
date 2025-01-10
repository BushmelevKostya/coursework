import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { keycloakConfig } from './keycloak.config';

@Injectable({
  providedIn: 'root',
})
export class AppKeycloakService {
  private tokenRefreshInterval: any;

  constructor(private keycloakService: KeycloakService) {}

  initKeycloak(): Promise<boolean> {
    return this.keycloakService.init({
      config: keycloakConfig,
      initOptions: {
        onLoad: 'login-required',
        checkLoginIframe: false,
        redirectUri: 'http://localhost:4200',
      },
      loadUserProfileAtStartUp: true,
    }).then(() => {
      return this.keycloakService.getToken().then(token => {
        localStorage.setItem('token', token!);
        this.startTokenRefresh();
        return true;
      });
    });
  }

  getToken(): Promise<string> {
    return this.keycloakService.getToken();
  }

  logout(): void {
    clearInterval(this.tokenRefreshInterval);
    this.keycloakService.logout();
  }

  isAuthenticated(): boolean {
    return this.keycloakService.isLoggedIn();
  }

  private startTokenRefresh(): void {
    this.tokenRefreshInterval = setInterval(() => {
      this.keycloakService.updateToken(30).then(refreshed => {
        if (refreshed) {
          this.keycloakService.getToken().then(token => {
            localStorage.setItem('token', token!);
            console.log('Token refreshed and saved to localStorage');
          });
        }
      }).catch(err => {
        console.error('Failed to refresh token', err);
        this.logout();
      });
    }, 10000);
  }
}
