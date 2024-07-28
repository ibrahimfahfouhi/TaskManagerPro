import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {StorageService} from "../../../auth/services/storage/storage.service";
import {Observable} from "rxjs";

const base_url = "http://localhost:8090/";
@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http: HttpClient) { }
  getEmployeeTasksById(): Observable<any>{
    return this.http.get(base_url + "api/employee/tasks", {
      headers: this.createAuthorizationHeader()
    })
  }
  private createAuthorizationHeader(): HttpHeaders {
    return new HttpHeaders().set(
      "Authorization", "Bearer " + StorageService.getToken()
    )
  }
}
