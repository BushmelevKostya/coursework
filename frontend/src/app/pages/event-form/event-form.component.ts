import { Component } from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {NavbarComponent} from '../../shared/navbar/navbar.component';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {RequestService} from '../../service/request.service';

@Component({
  selector: 'app-event-form',
  templateUrl: './event-form.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NavbarComponent,
    HttpClientModule
  ],
  styleUrls: ['./event-form.component.css'],
  providers: [HttpClientModule, RequestService]
})
export class EventFormComponent {
  constructor(private requestService: RequestService) {
  }

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

  onSubmit(form: NgForm) {
    this.requestService.createGameEvent(form);
  }
}
