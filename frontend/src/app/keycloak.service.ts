import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { keycloakConfig, keycloakInitOptions } from './keycloak.config';
import {jwtDecode, JwtDecodeOptions} from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class AppKeycloakService {
  private tokenRefreshInterval: any;

  constructor(private keycloakService: KeycloakService) {}

  initKeycloak(): Promise<boolean> {
    return this.keycloakService.init({
      config: keycloakConfig,
      initOptions: keycloakInitOptions,
      loadUserProfileAtStartUp: true,
    }).then(() => {
      return this.keycloakService.getToken().then(token => {
        localStorage.setItem('username', this.keycloakService.getUsername());
        localStorage.setItem('token', token!);
        this.startTokenRefresh();
        return true;
      });
    });
  }

  async getToken(): Promise<any> {
    const token = await this.keycloakService.getToken();
    if (!token) return null;
    try {
      return jwtDecode(token);
    } catch (error) {
      console.error('Ошибка декодирования токена', error);
      return null;
    }
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
            // console.log('Token refreshed and saved to localStorage:', token);
          });
        }
      }).catch(err => {
        console.error('Failed to refresh token', err);
        this.logout();
      });
    }, 10000);
  }
}
