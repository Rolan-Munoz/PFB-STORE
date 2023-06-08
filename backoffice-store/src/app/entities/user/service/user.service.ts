import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, of } from 'rxjs';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {



  constructor
  (private http: HttpClient) { }

  
  registerUser(user: User): Observable<HttpResponse<User>> {
    let urlEndpoint: string = "http://localhost:8080/store/users";
    return this.http.post<User>(urlEndpoint, user, { observe: 'response' });
  }
  
  loginUser(user: User): Observable<User> {
    let urlEndpoint: string = "http://localhost:8080/store/login";
    return this.http.post<User>(urlEndpoint, user);
  }
  
  logoutUser(): Observable<any> {
    let urlEndpoint: string = "http://localhost:8080/store/logout";
    return this.http.post<any>(urlEndpoint, {});
  }
  

  getUserById(userId: number): Observable<User> {
    let urlEndpoint: string = "http://localhost:8080/store/users/" + userId;
    return this.http.get<User>(urlEndpoint);
  }

  getUsers(): Observable<User[]> {
    let urlEndpoint: string = "http://localhost:8080/store/users/"
    return this.http.get<User[]>(urlEndpoint);
  }


  checkEmailExists(email: string): Observable<boolean> {
    let urlEndpoint: string = "http://localhost:8080/store/users/exists/email/" + email;
    return this.http.get<boolean>(urlEndpoint);
  }
  
  checkNickExists(nick: string): Observable<boolean> {
    let urlEndpoint: string = "http://localhost:8080/store/users/exists/nick/" + nick;
    return this.http.get<boolean>(urlEndpoint);
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

  getUserIdFromLocalStorage(): number | undefined {
    const sessionId = localStorage.getItem('sessionId');
    if (sessionId) {
      const userJson = localStorage.getItem('user');
      if (userJson) {
        const user: User = JSON.parse(userJson);
        if (user.id) {
          return user.id;
        }
      }
    }
    return undefined;
  }
  


}
