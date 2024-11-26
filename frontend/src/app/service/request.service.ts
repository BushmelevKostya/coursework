import {HttpClient, HttpHeaders} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class RequestService {
    constructor(private http: HttpClient) {
    }

    createGameEvent(form: NgForm) {
        const url = 'http://localhost:2585/api/events/create';
        // const token = localStorage.getItem('token');
        //
        // const headers = new HttpHeaders({
        //     Authorization: `Bearer ${token}`,
        //     'Content-Type': 'application/json',
        // });

        // console.log(headers);
        const httpOptions = {
          headers: new HttpHeaders({
            'Content-Type': 'application/json',
          })
        };
        this.http.post(url, form.value, httpOptions).subscribe(
            response => {
                console.log(response);
                console.log("Event was created!");
            },
            error => {
                console.error(error.message);
                console.error(error.error.message);
            }
        );
    }
}
