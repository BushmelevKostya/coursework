import {Component} from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {RequestService} from '../../service/request.service';
import {NgIf} from '@angular/common';
import {NavbarComponent} from '../../shared/navbar/navbar.component';

@Component({
  selector: 'app-management',
  templateUrl: './management.component.html',
  styleUrls: ['./management.component.css'],
  imports: [
    NgIf,
    FormsModule,
    NavbarComponent
  ],
  standalone: true,
  providers: [RequestService]
})
export class ManagementComponent {
  activeTab: string = 'city';

  city = {name: ''};
  district = {name: '', cityId: null};
  game = {name: '', description: '', minPlayers: 0, maxPlayers: 0};
  genre = {name: ''};
  location = {address: '', districtId: null};
  private data: any;

  constructor(private requestService: RequestService) {
  }

  changeTab(tab: string): void {
    this.activeTab = tab;
  }

  onSubmit(form: NgForm, endpoint: string): void {
    this.requestService.postInfo(form.value, `api/v1/${endpoint}`).subscribe(
      () => {
        alert(`${endpoint} создан успешно`);
        form.reset();
      },
      (error) => {
        console.error('Ошибка при создании:', error);
      }
    );
  }

  deleteAll(endpoint: string): void {
    if (confirm(`Вы уверены, что хотите удалить все ${endpoint}?`))
      this.requestService.deleteInfo(this.data, `api/v1/${endpoint}/delete-all`).subscribe(
        () => alert(`Все ${endpoint} удалены успешно`),
        (error) => console.error('Ошибка при удалении:', error)
      );
  }
}
