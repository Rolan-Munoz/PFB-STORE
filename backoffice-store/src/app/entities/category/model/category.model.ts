export class Category {
    id: number | undefined;
    name: string;
    description?: string;
    image?: string;

constructor(
    id: number | undefined, 
    name: string, 
    description?: string, 
    image?: string
) {
    this.id = id
    this.name = name
    this.description = description
    this.image = image
}}