import { KeycloakConfig, KeycloakInitOptions } from 'keycloak-js';

export const keycloakConfig: KeycloakConfig = {
  url: 'https://localhost:9443/',
  realm: 'devrealm',
  clientId: 'pub-dev',
};

export const keycloakInitOptions: KeycloakInitOptions = {
  onLoad: 'login-required',
  pkceMethod: 'S256',
  redirectUri: 'http://localhost:4200/icon',
  checkLoginIframe: false,
};
