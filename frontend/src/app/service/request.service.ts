import {HttpClient, HttpHeaders} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {Injectable} from "@angular/core";
import {KeycloakService} from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class RequestService {
  private url = 'http://localhost:2585/api';

  constructor(private http: HttpClient, private keycloakService: KeycloakService) {}

  sendUsersToBackend() {
    this.keycloakService.loadUserProfile().then((profile) => {
      this.keycloakService.getKeycloakInstance().loadUserInfo().then((userInfo: any) => {
        this.http.post(`${this.url}/users`, { users: userInfo }).subscribe(
          (response) => {
            console.log('Пользователи отправлены успешно', response);
          },
          (error) => {
            console.error('Ошибка при отправке пользователей', error);
          }
        );
      });
    });
  }

  getUserByEmail() {
    this.getUserEmail().then((email) => {
      if (email) {
        this.http.get(`${this.url}/user/${email}`).subscribe(
          (response) => {
            console.log('Данные пользователя:', response);
          },
          (error) => {
            console.error('Ошибка при получении данных пользователя', error);
          }
        );
      } else {
        console.error('Email пользователя не найден');
      }
    });
  }


  getUserEmail() {
    return this.keycloakService.loadUserProfile().then((profile) => {
      return profile.email;
    });
  }


  createGameEvent(form: NgForm) {
        // const token = localStorage.getItem('token');
        //
        // const headers = new HttpHeaders({
        //     Authorization: `Bearer ${token}`,
        //     'Content-Type': 'application/json',
        // });
        // console.log(headers);

        this.http.post(`${this.url}/events/create`, form.value).subscribe(
            response => {
                console.log(response);
                console.log("Event was created!");
            },
            error => {
                console.error("Error creating event", error);
            }
        );
    }
}
