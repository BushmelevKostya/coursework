import { Component } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  isLoggedIn = false;
  userAvatar = 'https://via.placeholder.com/40';

  login() {
    this.isLoggedIn = true;
  }

  goToProfile() {
    console.log('Переход в профиль');
  }
}
