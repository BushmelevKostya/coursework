import {Component, OnInit} from '@angular/core';
import {NgForOf, NgOptimizedImage} from '@angular/common';
import {NavbarComponent} from '../../shared/navbar/navbar.component';
import {RequestService} from '../../service/request.service';

interface Profile {
  id: number;
  name: string;
  icon: string;
}

interface Event {
  id: number;
  eventName: string;
  dateEvent: string;
  gameResult: string;
  profileResponseDTO: Profile;
}

interface ProfileResponseDTO {
  id: number;
  name: string | null;
  icon: string;
}

interface GameResponseDTO {
  id: number;
  name: string;
  description: string;
  minPlayers: number;
  maxPlayers: number;
}

interface GameItem {
  id: number;
  profileResponseDTO: ProfileResponseDTO;
  gameResponseDTO: GameResponseDTO;
}

interface GameData {
  content: GameItem[];
}

interface Statistics {
  totalGames: number;
  wins: number;
  winPercentage: number;
}

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  standalone: true,
  imports: [
    NgOptimizedImage,
    NavbarComponent,
    NgForOf
  ],
  styleUrls: ['./user-profile.component.css'],
  providers: [RequestService]
})

export class UserProfileComponent implements OnInit {
  profile: any;
  gameHistory: Event[] = [];
  favouriteGames: GameResponseDTO[] = [];
  statistics: Statistics | null = null;

  private profileUrl = 'api/v1/profile';
  private gameHistoryUrl = 'api/v1/gamehistory';
  private favouriteGamesUrl = 'api/v1/favouritegames';

  constructor(private requestService: RequestService) {}

  ngOnInit(): void {
    this.loadProfile();
    this.loadGameHistory();
    this.loadFavouriteGames();
  }

  private loadProfile(): void {
    this.requestService.getInfo(this.profileUrl + "/username").subscribe(
      (response) => {
        this.profile = response;
      },
      (error) => {
        console.error('Ошибка при загрузке профиля', error);
      }
    );
  }

  private loadGameHistory(): void {
    this.requestService.getInfo(this.gameHistoryUrl).subscribe(
      (response) => {
        this.gameHistory = response.content;
        this.calculateStatistics();
      },
      (error) => {
        console.error('Ошибка при загрузке истории мероприятий', error);
      }
    );
  }

  private loadFavouriteGames(): void {
    this.requestService.getInfo(this.favouriteGamesUrl).subscribe(
      (response: GameData) => {
        const currentProfileId = Number(localStorage.getItem("profileId"));
        this.favouriteGames = response.content
          .filter((gameItem: GameItem) => gameItem.profileResponseDTO.id !== currentProfileId)
          .map((gameItem: GameItem) => gameItem.gameResponseDTO);
      },
      (error) => {
        console.error("Ошибка при получении данных:", error);
      }
    );
  }


  private calculateStatistics(): void {
    const totalGames = this.gameHistory.length;
    const wins = this.gameHistory.filter((event) => event.gameResult === 'WIN').length;
    const winPercentage = totalGames > 0 ? Math.round((wins / totalGames) * 100) : 0;

    this.statistics = {
      totalGames,
      wins,
      winPercentage,
    };
  }
}
