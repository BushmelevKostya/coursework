import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {RequestService} from '../../service/request.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  standalone: true,
  imports: [
    NgForOf,
    HttpClientModule,
    NgIf
  ],
  styleUrls: ['./event-list.component.css'],
  providers: [RequestService]
})
export class EventListComponent implements OnInit{
  events: any[] = [];
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
