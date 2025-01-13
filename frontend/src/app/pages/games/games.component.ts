import {Component, OnInit} from '@angular/core';
import {NgForOf, NgOptimizedImage} from '@angular/common';
import {NavbarComponent} from '../../shared/navbar/navbar.component';
import {RequestService} from '../../service/request.service';

interface Game {
  id: number;
  image: string;
  name: string;
  minPlayers: number;
  maxPlayers: number;
  genre: string;
  isFavorite?: boolean;
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
export class GamesComponent implements OnInit{
  games: Game[] = [];
  url = 'api/v1/game';
  constructor(private requestService: RequestService) {

  }

  ngOnInit() {
    this.requestService.getInfo(this.url).subscribe(
      (response) => {
        this.games = response.content.map((game: Game) => ({
          ...game,
          isFavorite: false
        }));
      },
      (error) => {
        console.error('Ошибка при загрузке данных', error);
        this.games = [];
      }
    );
  }

  toggleFavorite(game: Game) {
    game.isFavorite = !game.isFavorite;
    const favoriteUrl = `api/v1/favouritegames`;
    const data = {
      gameId: game.id,
      profileId: localStorage.getItem("profileId")
    }
    if (game.isFavorite) {
      this.requestService.postInfo(data, favoriteUrl).subscribe(
        () => {
        },
        (error) => {
          game.isFavorite = !game.isFavorite;
        }
      );
    } else {
      this.requestService.deleteInfo(data, favoriteUrl).subscribe(
        () => {
        },
        (error) => {
          game.isFavorite = !game.isFavorite;
        }
      );
    }
  }
}
