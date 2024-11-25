import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: 'https://localhost:9443/',
  realm: 'realm',
  clientId: 'kos',
});

keycloak.init({ onLoad: 'login-required' }).then(authenticated => {
  if (authenticated) {
    console.log('Authenticated');
    localStorage.setItem('token', keycloak.token!);
  } else {
    console.error('Authentication failed');
  }
}).catch(err => console.error('Keycloak init failed', err));

setInterval(() => {
  keycloak.updateToken(30).then(refreshed => {
    if (refreshed) {
      localStorage.setItem('token', keycloak.token!);
    }
  }).catch(err => {
    console.error('Failed to refresh token', err);
    keycloak.logout();
  });
}, 10000);

export default keycloak;
