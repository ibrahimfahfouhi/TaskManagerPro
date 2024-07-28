import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {StorageService} from "../../../auth/services/storage/storage.service";
const base_url = "http://localhost:8090/";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }
  getUsers(): Observable<any>{
    return this.http.get(base_url + "api/admin/users", {
      headers: this.createAuthorizationHeader()
    })
  }
  postTask(taskDto: any): Observable<any>{
    return this.http.post(base_url + "api/admin/task", taskDto, {
      headers: this.createAuthorizationHeader()
    })
  }
  updateTask(id: number, taskDto: any): Observable<any>{
    return this.http.put(base_url + `api/admin/updateTask/${id}`, taskDto, {
      headers: this.createAuthorizationHeader()
    })
  }
  getAllTasks(): Observable<any>{
    return this.http.get(base_url + "api/admin/tasks", {
      headers: this.createAuthorizationHeader()
    })
  }
  deleteTask(id: number): Observable<any>{
    return this.http.delete(base_url + "api/admin/deleteTask/" + id, {
      headers: this.createAuthorizationHeader()
    })
  }
  getTaskById(id: number): Observable<any>{
    return this.http.get(base_url + "api/admin/getTask/" + id, {
      headers: this.createAuthorizationHeader()
    })
  }
  searchTask(title: string): Observable<any>{
    return this.http.get(base_url + "api/admin/searchTask/" + title, {
      headers: this.createAuthorizationHeader()
    })
  }
  private createAuthorizationHeader(): HttpHeaders {
    return new HttpHeaders().set(
      "Authorization", "Bearer " + StorageService.getToken()
    )
  }
}
