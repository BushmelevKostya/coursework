import { Component } from '@angular/core';
import {NgForOf} from '@angular/common';
import {HttpClient, HttpClientModule} from '@angular/common/http';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  standalone: true,
  imports: [
    NgForOf,
    HttpClientModule
  ],
  styleUrls: ['./event-list.component.css']
})
export class EventListComponent {
  constructor(private http: HttpClient) {

  }

  events = [
    { title: 'Мероприятие 1', description: 'Описание первого мероприятия', author: 'Иван', occupiedSeats: 5, totalSeats: 10 },
    { title: 'Мероприятие 2', description: 'Описание второго мероприятия', author: 'Мария', occupiedSeats: 3, totalSeats: 8 },
    { title: 'Мероприятие 3', description: 'Описание третьего мероприятия', author: 'Петр', occupiedSeats: 7, totalSeats: 7 },
  ];



  register(event: any) {

  }
}
