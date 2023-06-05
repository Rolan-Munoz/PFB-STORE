import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, of } from 'rxjs';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/store';

  constructor
  (private http: HttpClient) { }

  
  registerUser(user: User): Observable<HttpResponse<User>> {
    return this.http.post<User>(`${this.apiUrl}/users`, user, { observe: 'response' });
  }
  

  loginUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/login`, user);
  }

  logoutUser(): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/logout`, {});
  }

  getUserById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/users/${userId}`);
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/users`);
  }


  checkEmailExists(email: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/users/exists/email/${email}`);
  }

  checkNickExists(nick: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/users/exists/nick/${nick}`);
  }

  getNickFromLocalStorage(): string {
    const sessionId = localStorage.getItem('sessionId');
    if (sessionId) {
      const userJson = localStorage.getItem('user');
      if (userJson) {
        const user: User = JSON.parse(userJson);
        if (user.nick) {
          return user.nick;
        }
      }
    }
    return '';
  }


}
