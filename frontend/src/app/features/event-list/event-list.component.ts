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
        console.error('Ошибка при загрузке данных', error);
        this.events = [];
      }
    );
  }

  register(event: any) {

  }
}
