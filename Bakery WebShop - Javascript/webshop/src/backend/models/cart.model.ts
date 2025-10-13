export class CartItem {
  productId: string;
  name: string;
  price: number;
  quantity: number;

  constructor(productId: string, name: string, price: number, quantity: number) {
    this.productId = productId;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }
}

export class Cart {
  _id?: string;
  userId: string;
  cartItems: CartItem[];
  totalAmount: number;

  constructor(userId: string, cartItems: CartItem[] = [], _id?: string) {
    this._id = _id;
    this.userId = userId;
    this.cartItems = cartItems;
    this.totalAmount = this.calculateTotal();
  }

  addItem(item: CartItem) {
    const existingItem = this.cartItems.find(i => i.productId === item.productId);
    if (existingItem) {
      existingItem.quantity += item.quantity;
    } else {
      this.cartItems.push(item);
    }
    this.totalAmount = this.calculateTotal();
  }

  removeItem(productId: string) {
    this.cartItems = this.cartItems.filter(item => item.productId !== productId);
    this.totalAmount = this.calculateTotal();
  }

  updateQuantity(productId: string, quantity: number) {
    const item = this.cartItems.find(i => i.productId === productId);
    if (item) {
      if (quantity <= 0) {
        this.removeItem(productId);
      } else {
        item.quantity = quantity;
      }
      this.totalAmount = this.calculateTotal();
    }
  }

  increaseQuantity(productId: string) {
    const item = this.cartItems.find(i => i.productId === productId);
    if (item) {
      item.quantity++;
      this.totalAmount = this.calculateTotal();
    }
  }

  decreaseQuantity(productId: string) {
    const item = this.cartItems.find(i => i.productId === productId);
    if (item) {
      if (item.quantity > 1) {
        item.quantity--;
      } else {
        this.removeItem(productId);
      }
      this.totalAmount = this.calculateTotal();
    }
  }

  clearCart() {
    this.cartItems = [];
    this.totalAmount = 0;
  }

  private calculateTotal(): number {
    return this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
  }
}

