import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Category } from '../../category/model/category.model';
import { CategoryService } from '../../category/service/category.service';
import { Item } from '../model/intem.model';
import { ItemService } from '../service/item.service';

@Component({
  selector: 'app-item-reactive-form',
  templateUrl: './item-reactive-form.component.html',
  styleUrls: ['./item-reactive-form.component.scss']
})
export class ItemReactiveFormComponent implements OnInit {
  mode: "NEW" | "UPDATE" = "NEW";
  itemId?: number;
  item?: Item;
  selectedCategory?: Category;
  categories: Category[] = [];

  itemForm?: FormGroup;

  constructor(private route: ActivatedRoute,
              private itemService: ItemService,
              private categoryService: CategoryService,
              private fb: FormBuilder
              ) { }

  ngOnInit(): void {

    this.buildForm();

    const entryParam: string = this.route.snapshot.paramMap.get("itemId") ?? "new";
    if (entryParam !== "new") {
      this.itemId = +this.route.snapshot.paramMap.get("itemId")!;
      this.mode = "UPDATE";
      this.getItemById(this.itemId!);
    } else {
      this.mode = "NEW";
      this.initializeItem();
    }
    
  }

  public getAllCategories(event?: any): void {
    let categorySearch: string | undefined;
    if (event?.query) {
      categorySearch = event.query;
    }
    this.categoryService.getAllCategories(categorySearch).subscribe({
      next: (categoriesFiltered) => { this.categories = categoriesFiltered; },
      error: (err) => {this.handleError(err);}
    })
  }

  public saveItem(): void {
    const itemToSave: Item = this.createFromForm();
    if (this.mode === "NEW") {
      this.insertItem(itemToSave);
    }

    if (this.mode === "UPDATE") {
      this.updateItem(itemToSave);
    }
  }

  public categorySelected(): void {
    this.item!.categoryId = this.selectedCategory!.id;
    this.item!.categoryName = this.selectedCategory!.name;
  }

  public categoryUnselected(): void {
    this.item!.categoryId = undefined;
    this.item!.categoryName = undefined;
  }

  public includeImageInItem(event: any): void {
    const inputFile = event.target as HTMLInputElement;
    const file: File | null = inputFile.files?.item(0) ?? null;


    this.readFileAsString(file!).then(
      (result) => {
        const imageType: string = this.getImageType(result);
        console.log(imageType);
        const imageBase64: string = this.getImageBase64(result);
        console.log(imageBase64);

        this.item!.image = imageBase64;

      },
      (error) => {
        console.log("No se pudo cargar la imagen")
      })
        
  }

  private buildForm(): void {
    this.itemForm = this.fb.group({
      id: [{value: undefined, disabled: true}],
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      description: ['', [Validators.maxLength(2000)]],
      price: [ 0, [Validators.required, Validators.min(0)]],
      category: [undefined, [Validators.required]]
    })
  }

  private updateForm(item: Item): void {
    this.itemForm?.patchValue({
      id: item.id ,
      name: item.name ,
      description: item.description,
      price: item.price,
      category: new Category(item.categoryId!, item.categoryName!)
    })
  }

  private createFromForm(): Item {
    return {
      ...this.item,
      id: this.itemForm?.get(['id'])!.value,
      name: this.itemForm?.get(['name'])!.value,
      description: this.itemForm?.get(['description'])!.value,
      price: this.itemForm?.get(['price'])!.value,
      image: this.item!.image,
      categoryId: this.itemForm?.get(['category'])!.value.id,
      categoryName: this.itemForm?.get(['category'])!.value.name,
    };
  }

  private getImageType(imageString: string): string {
    const imageStringParts: string[] = imageString.split(",");
    if (imageStringParts.length == 2) {
      return imageStringParts[0];
    } else {
      return "";
    }
  }

  private getImageBase64(imageString: string): string {
    const imageStringParts: string[] = imageString.split(",");
    if (imageStringParts.length == 2) {
      return imageStringParts[1];
    } else {
      return "";
    }
  }


  private readFileAsString(file: File) {
    return new Promise<string>(function(resolve, reject) {
      let reader: FileReader = new FileReader();
      reader.readAsDataURL(file)
      reader.onload = function() {
        resolve(this.result as string)
      }
    })
  }

  private insertItem(itemToSave: Item): void {
      this.itemService.insert(itemToSave).subscribe({
          next: (itemInserted) => {
            console.log("Insertado correctamente");
            console.log(itemInserted);
          },
          error: (err) => {this.handleError(err);}
      })
  }

  private updateItem(itemToSave: Item): void {
    this.itemService.update(itemToSave).subscribe({
      next: (itemUpdated) => {
        console.log("Modificado correctamente");
        console.log(itemUpdated);
      },
      error: (err) => {this.handleError(err);}
  })
  }

  private getItemById(itemId: number) {
    this.itemService.getItemById(itemId).subscribe({
      next: (itemRequest) => {
        this.item = itemRequest;
        this.updateForm(itemRequest);
        this.selectedCategory = new Category(itemRequest.categoryId!, itemRequest.categoryName!);
      },
      error: (err) => {this.handleError(err);}
    })
  }

  private initializeItem(): void {
    this.item = new Item(undefined, "", 0);
  }
  
  private handleError(err: any): void {
    // ToDo
  }

}
