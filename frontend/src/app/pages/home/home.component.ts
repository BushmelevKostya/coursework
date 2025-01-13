import { Component } from '@angular/core';
import {EventListComponent} from '../../features/event-list/event-list.component';
import {NavbarComponent} from '../../shared/navbar/navbar.component';
import {RouterOutlet} from '@angular/router';
import {RequestService} from '../../service/request.service';
import {NgIf} from '@angular/common';
import {KudagoEventListComponent} from '../../features/kudago-event-list/kudago-event-list.component';
import {RecommendedEventListComponent} from '../../features/recommended-event-list/recommended-event-list.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    EventListComponent,
    NavbarComponent,
    RouterOutlet,
    NgIf,
    KudagoEventListComponent,
    RecommendedEventListComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  providers: [RequestService]
})
export class HomeComponent {
  constructor(private requestService:RequestService) {
  }
  activeTab: string = 'game';

  changeTab(tab: string): void {
    this.activeTab = tab;
  }
}
