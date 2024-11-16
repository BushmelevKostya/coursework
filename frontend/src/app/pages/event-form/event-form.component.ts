import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {NavbarComponent} from '../../shared/navbar/navbar.component';

@Component({
  selector: 'app-event-form',
  templateUrl: './event-form.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NavbarComponent
  ],
  styleUrls: ['./event-form.component.css']
})
export class EventFormComponent {
  event = {
    name: '',
    description: '',
    date: '',
    minMembers: 0,
    maxMembers: 0,
    organiserId: 0,
    locationId: 0,
    statusId: 0,
    game: ''
  };

  onSubmit() {
    console.log('Мероприятие создано:', this.event);
  }
}
