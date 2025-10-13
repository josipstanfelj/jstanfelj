export class Order {
  _id?: string;
  userId: string;
  products: { productId: string, quantity: number }[];
  totalAmount: number;
  status: 'pending' | 'completed' | 'cancelled';
  createdAt?: Date;


  constructor(userId: string, products: { productId: string, quantity: number }[], totalAmount: number, status: 'pending' | 'completed' | 'cancelled', _id?: string, createdAt?: Date) {
    this.userId = userId;
    this.products = products;
    this.totalAmount = totalAmount;
    this.status = status;
    this._id = _id;
    this.createdAt = createdAt;
  }

}
