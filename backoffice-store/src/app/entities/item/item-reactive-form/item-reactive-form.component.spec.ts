import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemReactiveFormComponent } from './item-reactive-form.component';

describe('ItemReactiveFormComponent', () => {
  let component: ItemReactiveFormComponent;
  let fixture: ComponentFixture<ItemReactiveFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ItemReactiveFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ItemReactiveFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
