import { Component, OnInit } from '@angular/core';
import {RequestService} from '../../service/request.service';
import {NavbarComponent} from '../../shared/navbar/navbar.component';
import {DatePipe, NgForOf} from '@angular/common';

@Component({
  selector: 'app-user-events',
  templateUrl: './user-events.component.html',
  styleUrls: ['./user-events.component.css'],
  standalone: true,
  imports: [
    NavbarComponent,
    NgForOf,
    DatePipe
  ],
  providers: [RequestService]
})

export class UserEventsComponent implements OnInit {
  private readonly apiUrl = '/api/v1/gameeventprofiles';

  userEvents: Array<{
    id: number;
    name: string;
    description: string;
    date: string;
  }> = [];

  constructor(private requestService: RequestService) {}

  ngOnInit(): void {
    this.requestService.getInfo(this.apiUrl)
      .subscribe((events) => {
          const currentProfileId = Number(localStorage.getItem("profileId"));
          this.userEvents = events.content
            .filter((gameItem:any) => gameItem.profileResponseDTO.id !== currentProfileId)
            .map((gameItem: any) => gameItem.gameEventResponseDTO);
    });
  }
}
