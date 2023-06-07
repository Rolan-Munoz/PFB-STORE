import { Item } from "../item/model/item.model";
import { User } from "../user/model/user.model";

export class Favorite {
    id: number;
    user: User;
    item: Item;
    constructor(id: number, user: User, item: Item) {
        this.id = id;
        this.user = user;
        this.item = item;
    }
}
