import {HttpClient, HttpHeaders} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {Injectable} from "@angular/core";
import {Observable} from 'rxjs';

@Injectable()
export class RequestService {
  constructor(private http: HttpClient) {
  }

  url = 'http://localhost:8080/';

  postInfo(data: any, url: string):Observable<any> {
    let token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.post(this.url + url, data, {headers});
  }

  getInfo(url: string):Observable<any> {
    let token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.get<{ content: any[] }>(this.url + url, {headers});
  }

  test() {
    let token = localStorage.getItem('token');
    // let token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlT05BVHY3eVN3MExVODdweU15bVBEVi0tUXNzdlNpT1NBNExnR2ZQTFlvIn0.eyJleHAiOjE3MzY2MTA2NzksImlhdCI6MTczNjYxMDM3OSwiYXV0aF90aW1lIjoxNzM2NjEwMzc5LCJqdGkiOiJiYjRlYzY1ZS00YzU1LTRhMGQtOTE1Yy02YjlkYTIwOTQ4ZmQiLCJpc3MiOiJodHRwczovL2xvY2FsaG9zdDo5NDQzL3JlYWxtcy9kZXZyZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJkNjYzNmQ1Zi0xMjljLTQ2YjEtYTE1Yy01YjkxMzkyMzJjYjciLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJwdWItZGV2Iiwibm9uY2UiOiJwMHAzYmcyYzUxIiwic2Vzc2lvbl9zdGF0ZSI6ImQ2M2MwNTI2LWVmYjUtNDExNy05YjhiLWEwMGY3NTQ5YTc4MyIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1kZXZyZWFsbSIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJVU0VSIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgZW1haWwgcHJvZmlsZSIsInNpZCI6ImQ2M2MwNTI2LWVmYjUtNDExNy05YjhiLWEwMGY3NTQ5YTc4MyIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6ItCa0L7QvdGB0YLQsNC90YLQuNC9IGsiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJrIiwiZ2l2ZW5fbmFtZSI6ItCa0L7QvdGB0YLQsNC90YLQuNC9IiwiZmFtaWx5X25hbWUiOiJrIiwiZW1haWwiOiJrQGdtYWlsLmNvbSJ9.FOAXc5MdVTgGWmdIoO8KjONW3zZ7-5M505V7iHffAF_nI8w8zKSLFjKrVFrR-3DBynoEKUXee0nXvZT60gUk-_ndSSw3vT_xedm_3GyL-YOTgMi89-22KUomuUW-KOIJj585CVYArkbkcINr8mOG0EG9PydMiARoz40lYAjQhdlE4mXfP0NSowmuD2BUQ3-D0GWydBa25aE0RXnYV69GTFyBNzCIGWfRkuDxC-wsjibwo4zo42Q5l6o1bCs-n0UddGPnHdpFSyaNXKm7b7p1YIJ5-Gg9a_7TtfeC_-iLDdHAtI1n0G8ZItRXfdHuUtYT3dbDWyFJeEDCKA9-eVXnpA";
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
    this.http.get(this.url + "api/v1/gameevent", {headers}).subscribe(
      response => console.log(response),
      error => alert(error)
    );
  }
}
