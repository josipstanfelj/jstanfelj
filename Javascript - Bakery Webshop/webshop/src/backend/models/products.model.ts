export class Product {
  _id: string;
  name: string;
  description: string;
  price: number;
  stock: number;
  category: string;
  image: string;

  constructor(name: string, description: string, price: number, stock: number, category: string, image: string, _id: string) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.stock = stock;
    this.category = category;
    this.image = image;
    this._id = _id;
  }
}

