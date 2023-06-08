import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Favorite } from '../favorite.model';

@Injectable({
  providedIn: 'root'
})
export class FavoriteService {
  constructor(private http: HttpClient) {}

  addFavorite(userId: number, itemId: number): Observable<any> {
    let urlEndpoint: string = "http://localhost:8080/store/favorites/users/" + userId + "/favorites/" + itemId;
    return this.http.post<any>(urlEndpoint, {});
  }

  removeFavorite(userId: number, itemId: number): Observable<any> {
    let urlEndpoint: string = "http://localhost:8080/store/favorites/users/" + userId + "/favorites/" + itemId;
    return this.http.delete<any>(urlEndpoint);
  }

  getUserFavorites(userId: number): Observable<Favorite[]> {
    let urlEndpoint: string = "http://localhost:8080/store/favorites/users/" + userId + "/favorites";
    return this.http.get<Favorite[]>(urlEndpoint);
  }
  

  checkIfItemIsFavorite(userId: number, itemId: number): Observable<boolean> {
    let urlEndpoint: string = "http://localhost:8080/store/favorites/users/" + userId + "/items/" + itemId;
    return this.http.get<boolean>(urlEndpoint);
  }
}

