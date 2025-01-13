import { Component, OnInit } from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {RequestService} from '../../service/request.service';

@Component({
  selector: 'app-recommended-event-list',
  templateUrl: './recommended-event-list.component.html',
  styleUrls: ['./recommended-event-list.component.css'],
  imports: [
    NgForOf,
    NgIf
  ],
  standalone: true,
  providers: [RequestService]
})
export class RecommendedEventListComponent implements OnInit {
  events: any[] = [];
  apiUrl = '/api/v1/gameevent/recommended';

  constructor(private requestService: RequestService) {}

  ngOnInit(): void {
    this.requestService.getInfo(this.apiUrl + "/" + `${Number(localStorage.getItem("profileId"))}`).subscribe(
      (data) => {
        this.events = data;
      },
      (error) => {
        this.events = [];
      }
    );
  }

  register(event: any): void {
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
