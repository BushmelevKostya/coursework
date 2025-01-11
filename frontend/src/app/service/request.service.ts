import {HttpClient, HttpHeaders} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {Injectable} from "@angular/core";

@Injectable()
export class RequestService {
    constructor(private http: HttpClient) {
    }

  url = 'http://localhost:8080/api/v1';

  createGameEvent(form: NgForm) {
        let token = localStorage.getItem('token');
        const headers = new HttpHeaders({
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
        });

        console.log(headers);

        this.http.post(this.url + "/events/create", form.value, { headers }).subscribe(
            response => {
                console.log(response);
                console.log("Event was created!");
            },
            error => {
                console.error("Error creating event", error);
            }
        );
    }

    test() {
      // let token = localStorage.getItem('token');
      // token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlT05BVHY3eVN3MExVODdweU15bVBEVi0tUXNzdlNpT1NBNExnR2ZQTFlvIn0.eyJleHAiOjE3MzY1OTE5NzIsImlhdCI6MTczNjU5MTY3MiwiYXV0aF90aW1lIjoxNzM2NTg5OTk5LCJqdGkiOiJiN2MyYzNjZi0wMDA1LTQzMDEtYTc5ZS1lMzhjOTY5YjMyMDEiLCJpc3MiOiJodHRwczovL2xvY2FsaG9zdDo5NDQzL3JlYWxtcy9kZXZyZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJkNTMyOWY0YS1iZjA0LTQwNWMtYjRkOS1kMDYxMDdiY2VmODUiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJwdWItZGV2Iiwibm9uY2UiOiJvNjJjZ25mMXdvIiwic2Vzc2lvbl9zdGF0ZSI6ImYxZTIxZTBiLWNjZDQtNDQzOC1hOTlhLWQ4OWVhNTczMzAzNSIsImFjciI6IjAiLCJhbGxvd2VkLW9yaWdpbnMiOlsiKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1kZXZyZWFsbSIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJVU0VSIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgZW1haWwgcHJvZmlsZSIsInNpZCI6ImYxZTIxZTBiLWNjZDQtNDQzOC1hOTlhLWQ4OWVhNTczMzAzNSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6ItCa0L7QvdGB0YLQsNC90YLQuNC9IGtvcyIsInByZWZlcnJlZF91c2VybmFtZSI6ImtvcyIsImdpdmVuX25hbWUiOiLQmtC-0L3RgdGC0LDQvdGC0LjQvSIsImZhbWlseV9uYW1lIjoia29zIiwiZW1haWwiOiJrb3NAZ21haWwuY29tIn0.Na29z0KKnJpRJDSPk-rE3OXBrY1VrYaePWGjTL9axGPZcMEQMig1fE92yOpqQbrocDx5o0AtcCz5-oinZJ2qeIyr-AVmrco5m-fjKwyuQHiVRYFWZbSUYeBsEGeCJ6tcga7Jx7iir7PMB63SUQXxPJauiKNECtn1VyVYmuIN6p74MoNG9F4l6507GoJf4YgKCS2Qtb4SxDYwo4q_OoCKLHRxp0KaBh83N93ijcQ0aDKRNDC9i4KBu8SgTxkVUaa9L7bAhyGbVb_vzrYpkpn2Az6g4pltjJT8XaAypcyGyWpNmJGLuPoJccCV5o9QdIX1DDY9bN_8Ig0mYxxa-hqcsA"
      // const headers = new HttpHeaders({
      //   Authorization: `Bearer ${token}`,
      //   // 'Content-Type': 'application/json',
      // });
      //
      // this.http.get(this.url + "/test", {headers}).subscribe(
      this.http.get(this.url + "/test", {
        headers: new HttpHeaders({
          Authorization: `Bearer ${localStorage.getItem('token')}`
        })
      }).subscribe(
        response => console.log(response),
        error => console.error('Error:', error)
      );
    }
}
