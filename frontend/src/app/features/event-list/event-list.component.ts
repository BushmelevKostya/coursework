import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {RequestService} from '../../service/request.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  standalone: true,
  imports: [
    NgForOf,
    HttpClientModule,
    NgIf,
    FormsModule
  ],
  styleUrls: ['./event-list.component.css'],
  providers: [RequestService]
})
export class EventListComponent implements OnInit{
  events: any[] = [];
  filters: any = {
    name: '',
    description: '',
    gameName: '',
    locationName: '',
    statusName: '',
    minMembers: null,
    maxMembers: null
  };
  url = 'api/v1/gameevent';
  constructor(private requestService: RequestService) {

  }

  ngOnInit() {
    this.requestService.getInfo(this.url).subscribe(
      (response) => {
        this.events = response.content;
      },
      (error) => {
        this.events = [];
      }
    );
  }

  filterEvents() {
    const queryParams = new URLSearchParams()
    Object.keys(this.filters).forEach((key) => {
      if (this.filters[key] !== null && this.filters[key] !== '') {
        queryParams.append(key, this.filters[key].toString());
      }
    });

    const filterUrl = `${this.url}/filter?${queryParams.toString()}`;

    this.requestService.getInfo(filterUrl).subscribe(
      (response) => {
        this.events = response.content;
      },
      () => {
        this.events = [];
      }
    );
  }


  register(event: any) {
    const data = {
      eventId: event.id,
      profileId: localStorage.getItem("profileId")
    }
    this.requestService.postInfo(data, 'api/v1/gameeventprofiles').subscribe(
      (response) => {
        alert("Вы успешно зарегистрированы на событие!")
      },
      (error) => {
      }
    );
  }
}
