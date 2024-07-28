import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ResourceTypeForDiagnostics} from "@angular/compiler-cli/src/ngtsc/annotations/component/src/resources";
const base_url = "http://localhost:8090/"
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }
  signup(signupRequest: any): Observable<any> {
    return this.http.post(base_url + "api/auth/signup", signupRequest);
  }
  login(loginRequest: any): Observable<any> {
    return this.http.post(base_url + "api/auth/login", loginRequest);
  }
}
