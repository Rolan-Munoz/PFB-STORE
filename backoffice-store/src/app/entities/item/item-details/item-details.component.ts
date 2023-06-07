import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { FavoriteService } from '../../favorite/service/favorite.service';

import { Item } from '../model/item.model';
import { ItemService } from '../service/item.service';

@Component({
  selector: 'app-item-details',
  templateUrl: './item-details.component.html',
  styleUrls: ['./item-details.component.scss']
})
export class ItemDetailsComponent implements OnInit {
  itemId?: string;
  item?: Item;
  isFavorite: boolean = false;
  userId?: number; 
  showFavoriteMessage: boolean =false

  constructor(
    private route: ActivatedRoute,
    private itemService: ItemService,
    private favoriteService: FavoriteService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.itemId = params['itemId'];
      if (this.itemId) {
        const id = Number(this.itemId);
        this.itemService.getItemById(id).subscribe(item => {
          this.item = item;
          
          this.userId = Number(localStorage.getItem('id'));
          localStorage.setItem('id', this.userId.toString());
          console.log("User ID stored in localStorage:", this.userId);

          if (this.userId) {
            this.checkIfItemIsFavorite(id);
          }
        });
      }
    });
  }
  
  
  

  toggleFavorite(): void {
    const itemId = Number(this.itemId);
    if (this.isFavorite) {
      this.userId && this.favoriteService.removeFavorite(this.userId, itemId).subscribe(() => {
        this.isFavorite = false;
        this.showFavoriteMessage = true;
        setTimeout(() => {
          this.showFavoriteMessage = false;
        }, 2000);
      });
    } else {
      this.userId && this.favoriteService.addFavorite(this.userId, itemId).subscribe(() => {
        this.isFavorite = true;
        this.showFavoriteMessage = true;
        setTimeout(() => {
          this.showFavoriteMessage = false;
        }, 2000);
      });
    }
  }
  
  checkIfItemIsFavorite(itemId: number): void {
    this.userId && this.favoriteService.checkIfItemIsFavorite(this.userId, itemId).subscribe(isFavorite => {
      this.isFavorite = isFavorite;
    });
  }
}
  

