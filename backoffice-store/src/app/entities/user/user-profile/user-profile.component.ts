import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FavoriteService } from '../../favorite/service/favorite.service';
import { Item } from '../../item/model/item.model';
import { ItemService } from '../../item/service/item.service';


import { User } from '../model/user.model';
import { UserService } from '../service/user.service';

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
    private itemService: ItemService,
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    // Obtener el userId de la URL
    const userId = Number(this.route.snapshot.paramMap.get('userId'));
  
    // Hacer la petición al servidor para obtener la información del usuario
    this.userService.getUserById(userId).subscribe(user => {
      this.user = user;
  
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
    });
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
