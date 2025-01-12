import { Component } from '@angular/core';
import {NgForOf, NgOptimizedImage} from '@angular/common';
import {NavbarComponent} from '../../shared/navbar/navbar.component';
import {RequestService} from '../../service/request.service';

interface Game {
  image: string;
  name: string;
  players: string;
  genre: string;
}

@Component({
  selector: 'app-games',
  templateUrl: './games.component.html',
  standalone: true,
  imports: [
    NgOptimizedImage,
    NgForOf,
    NavbarComponent
  ],
  styleUrls: ['./games.component.css'],
  providers: [RequestService]
})
export class GamesComponent {
  games: any[] = [];
  url = 'api/v1/game';
  constructor(private requestService: RequestService) {

  }

  ngOnInit() {
    this.requestService.getInfo(this.url).subscribe(
      (response) => {
        this.games = response.content;
        console.log(this.games);
      },
      (error) => {
        console.error('Ошибка при загрузке данных', error);
        this.games = [];
      }
    );
  }
}
