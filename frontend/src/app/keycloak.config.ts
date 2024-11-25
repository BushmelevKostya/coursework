import {KeycloakConfig} from 'keycloak-js';

export const keycloakConfig: KeycloakConfig = {
  url: 'https://localhost:9443/',
  realm: 'realm',
  clientId: 'kos',
};
