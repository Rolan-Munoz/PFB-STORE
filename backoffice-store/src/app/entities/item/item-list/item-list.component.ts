import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Item } from '../model/item.model';
import { ItemService } from '../service/item.service';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.scss']
})
export class ItemListComponent implements OnInit {

  categoryId?: number;
  tittle: string = "";
  items: Item[] =[];

  page: number = 0;
  size: number = 5;
  sort: string = "name,asc";

  first: boolean = false;
  last: boolean = false;
  totalPages: number = 0;
  totalElements: number = 0;

  nameFilter?: string;
  priceFilter?: number;

  itemIdToDelete?: number;


  constructor(
    private route: ActivatedRoute,
    private itemService: ItemService,
    private router: Router
  ) {}



  ngOnInit(): void {
      if (this.route.snapshot.paramMap.get("categoryId")) {
        this.categoryId = +this.route.snapshot.paramMap.get("categoryId")!;
        this.tittle = "Articulos de la categoria " + this.categoryId;
        
      } 
      else {
        this.tittle = "Lista de Articulos";
        
      }this.getAllItems();
  }


  getItemDetails(itemId: number): void {
    this.router.navigate(['items', itemId]);
  }

  public nextPage(): void {
    this.page = this.page + 1;
    this.getAllItems();
  }

  public previusPage(): void {
    this.page = this.page - 1;
    this.getAllItems();
  } 

  public searchByFilter(): void {
    this.getAllItems();
  }

  public prepareItemToDelete(itemId: number): void {
    this.itemIdToDelete = itemId;
  }

  public deleteItem(): void {
    if (this.itemIdToDelete) {
      this.itemService.deleteItem(this.itemIdToDelete).subscribe({
      next: (data) => {
        this.getAllItems();
      },
      error: (err) => {this.handleError(err)}
      })
    }
    
  }

  


  private buildFilters(): string | undefined  {
    const filters: string[] = [];

    if(this.categoryId) {
      filters.push("category.id:EQUAL:" + this.categoryId)
    }

    if(this.nameFilter) {
      filters.push("name:MATCH:"+ this.nameFilter) ;
    } 

    if(this.priceFilter) {
      filters.push("price:LESS_THAN_EQUAL:" + this.priceFilter) ;
    }

    if(filters.length > 0){
      let globalFilters: string = "";
      for(let filter of filters) {
        globalFilters = globalFilters + filter + ",";
      }
      globalFilters = globalFilters.substring(0, globalFilters.length-1);
      return globalFilters;
    } else {
      return undefined;
  }
}

  private getAllItems(): void {

    const filters:string | undefined = this.buildFilters();

    this.itemService.getAllItems(this.page, this.size, this.sort, filters).subscribe({
      next: (data: any) => {
        this.items = data.content;
        this.first = data.first;
        this.last = data.last;
        this.totalPages = data.totalPages;
        this.totalElements = data.totalElements;
      },
      error: (err) => {this.handleError(err);}
    })
  }



  private handleError(error: any) {
    // lo que queramos poner.
  }



}
