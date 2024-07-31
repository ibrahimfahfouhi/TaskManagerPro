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
  updateTaskStatus(id: number, status: string): Observable<any>{
    return this.http.put(base_url + `api/employee/updateTask/${id}/${status}`, {
      headers: this.createAuthorizationHeader()
    })
  }
  getTaskById(id: number): Observable<any>{
    return this.http.get(base_url + "api/employee/getTask/" + id, {
      headers: this.createAuthorizationHeader()
    })
  }
  getCommentsByTaskId(id: number): Observable<any>{
    return this.http.get(base_url + "api/employee/taskComments/" + id, {
      headers: this.createAuthorizationHeader()
    })
  }
  createComment(id: number, content: string): Observable<any>{
    const params = {
      content: content
    }
    return this.http.post(base_url + "api/employee/createComment/" + id, null, {
      params: params,
      headers: this.createAuthorizationHeader()
    })
  }
  private createAuthorizationHeader(): HttpHeaders {
    return new HttpHeaders().set(
      "Authorization", "Bearer " + StorageService.getToken()
    )
  }
}
