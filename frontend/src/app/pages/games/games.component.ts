import { Component } from '@angular/core';
import {NgForOf, NgOptimizedImage} from '@angular/common';
import {NavbarComponent} from '../../shared/navbar/navbar.component';

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
  styleUrls: ['./games.component.css']
})
export class GamesComponent {
  games: Game[] = [
    {
      image: 'assets/images/g1.jpg',
      name: 'Эверделл',
      players: '1-4',
      genre: 'Евро',
    },
    {
      image: 'assets/images/g2.jpg',
      name: 'Дюна',
      players: '2-5',
      genre: 'Worker placement'
    },
    {
      image: 'assets/images/g3.jpg',
      name: 'Остров духов',
      players: '1-4',
      genre: 'Стратегия'
    }
  ];
}
