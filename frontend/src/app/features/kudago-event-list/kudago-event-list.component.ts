import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {NgForOf, NgIf} from '@angular/common';
import {RequestService} from '../../service/request.service';

@Component({
  selector: 'app-kudago-event-list',
  templateUrl: './kudago-event-list.component.html',
  styleUrls: ['./kudago-event-list.component.css'],
  imports: [
    NgForOf,
    NgIf
  ],
  standalone: true
})
export class KudagoEventListComponent implements OnInit {
  events: any[] = [];
  apiUrl = 'api/v1/otherevent';

  constructor(private requestService: RequestService) {}

  ngOnInit(): void {
    this.requestService.getInfo(this.apiUrl).subscribe(
      (data) => {
        this.events = data.content;
      },
      (error) => {
      }
    );
  }

  viewDetails(event: any): void {
    alert(`Детали события: ${event.description}`);
  }
}
