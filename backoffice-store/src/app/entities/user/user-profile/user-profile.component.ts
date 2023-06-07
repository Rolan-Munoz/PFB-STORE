import { Component, OnInit } from '@angular/core';
import { FavoriteService } from '../../favorite/service/favorite.service';
import { Item } from '../../item/model/item.model';
import { ItemService } from '../../item/service/item.service';


import { User } from '../model/user.model';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  user: User | null = null;
  items: Item[] = [];

  constructor(
    private favoriteService: FavoriteService,
    private itemService: ItemService
  ) {}

  ngOnInit(): void {
  const user = localStorage.getItem('user');
  if (user) {
    this.user = JSON.parse(user);

    if (this.user?.id) {
      this.favoriteService.getUserFavorites(this.user.id).subscribe(items => {
        items.forEach(item => {
          console.log(item.item.id)
          const itemId = Number(item.item.id);
          this.itemService.getItemById(itemId).subscribe(item => {
            this.items.push(item);
          });
        });
      });
    }
    } 
  }

  removeFromFavorites(item: Item): void {
    if (this.user?.id && item.id) {
      this.favoriteService.removeFavorite(this.user.id, item.id).subscribe(() => {
        if (this.user?.id) {
          this.favoriteService.getUserFavorites(this.user.id).subscribe(favorites => {
            this.items = favorites.map(favorite => favorite.item);
          });
        }
      });
    }
  }
  
}
