import {Component} from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {NavbarComponent} from '../../shared/navbar/navbar.component';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {RequestService} from '../../service/request.service';
import {ZonedDateTime, LocalDateTime, ZoneId, DateTimeFormatter} from '@js-joda/core';

@Component({
  selector: 'app-event-form',
  templateUrl: './event-form.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NavbarComponent,
    HttpClientModule
  ],
  styleUrls: ['./event-form.component.css'],
  providers: [HttpClientModule, RequestService]
})
export class EventFormComponent {
  constructor(private requestService: RequestService) {
  }

  event = {
    name: '1',
    description: '1',
    date: '2024-11-11T11:11:00+03:00',
    minMembers: 1,
    maxMembers: 1,
    organiserId: 1,
    locationId: 1,
    statusId: 1,
    game: 1
  };

  onSubmit(form: NgForm) {
    // form.value.date = this.convertToZonedDateTime(form.value.date, 'Europe/Moscow')
    this.requestService.postInfo(form.value, "api/v1/gameevent");
  }

  private convertToZonedDateTime(localDateTime: string, timeZone: string): string {
    const parsedLocalDateTime = LocalDateTime.parse(localDateTime);
    const zonedDateTime = parsedLocalDateTime.atZone(ZoneId.SYSTEM);
    return zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME).replace("[SYSTEM]", "");
  }
}
