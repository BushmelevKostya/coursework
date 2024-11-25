import { Component } from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {NavbarComponent} from '../../shared/navbar/navbar.component';
import {HttpClient, HttpClientModule} from '@angular/common/http';

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
  providers: [HttpClientModule]
})
export class EventFormComponent {
  constructor(private http: HttpClient) {
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
    const url = 'http://localhost:2584/api/events/create';
    this.http.post(url, form.value).subscribe(
      response => {
        console.log(response)
        console.log("Event was created!")
      },
      error => {
        console.error("Error creating movie", error)
      }
    )
  }
}
