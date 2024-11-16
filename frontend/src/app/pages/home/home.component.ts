import { Component } from '@angular/core';
import {EventListComponent} from '../../features/event-list/event-list.component';
import {NavbarComponent} from '../../shared/navbar/navbar.component';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    EventListComponent,
    NavbarComponent,
    RouterOutlet
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'

})
export class HomeComponent {

}
