import {HttpClient, HttpHeaders} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {Injectable} from "@angular/core";

@Injectable()
export class RequestService {
    constructor(private http: HttpClient) {
    }

    createGameEvent(form: NgForm) {
        const url = 'http://localhost:2584/api/events/create';
        const token = localStorage.getItem('token');

        const headers = new HttpHeaders({
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
        });

        console.log(headers);

        this.http.post(url, form.value, { headers }).subscribe(
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
