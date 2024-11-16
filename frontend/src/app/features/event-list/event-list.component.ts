import { Component } from '@angular/core';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.css']
})
export class EventListComponent {
  events = [
    { title: 'Мероприятие 1', description: 'Описание первого мероприятия', author: 'Иван' },
    { title: 'Мероприятие 2', description: 'Описание второго мероприятия', author: 'Мария' },
    { title: 'Мероприятие 3', description: 'Описание третьего мероприятия', author: 'Петр' },
  ];
}
