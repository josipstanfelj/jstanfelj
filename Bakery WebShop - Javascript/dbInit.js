const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
  username: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  name: { type: String, required: true },
  email: { type: String, required: true, unique: true },
  role: { type: String, enum: ['admin', 'customer', 'moderator'], default: 'customer' },
  place: { type: String, default: '' },
  postalCode: { type: String, default: '' },
  street: { type: String, default: '' },
  phone: { type: String, default: '' },
  createdAt: { type: Date, default: Date.now }
});

const productSchema = new mongoose.Schema({
  name: { type: String, required: true },
  description: { type: String, required: true },
  price: { type: Number, required: true },
  stock: { type: Number, required: true },
  category: { type: mongoose.Schema.Types.ObjectId, ref: 'Category', required: true },
  image: { type: String, required: true },
  createdAt: { type: Date, default: Date.now }
});

const orderSchema = new mongoose.Schema({
  userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
  products: [
    {
      productId: { type: mongoose.Schema.Types.ObjectId, ref: 'Product', required: true },
      quantity: { type: Number, required: true }
    }
  ],
  totalAmount: { type: Number, required: true },
  status: { type: String, enum: ['pending', 'completed', 'cancelled'], default: 'pending' },
  createdAt: { type: Date, default: Date.now }
});

const categorySchema = new mongoose.Schema({
  name: { type: String, required: true, unique: true },
  description: { type: String },
  createdAt: { type: Date, default: Date.now }
});

const cartSchema = new mongoose.Schema({
  userId: { type: String, required: true, unique: true },
  cartItems: [
    {
      productId: { type: mongoose.Schema.Types.ObjectId, ref: 'Product', required: true },
      name: { type: String, required: true },
      price: { type: Number, required: true },
      quantity: { type: Number, required: true }
    }
  ]
}, { timestamps: true });

const User = mongoose.model('User', userSchema);
const Product = mongoose.model('Product', productSchema);
const Order = mongoose.model('Order', orderSchema);
const Category = mongoose.model('Category', categorySchema);
const Cart = mongoose.model('Cart', cartSchema);

const MONGO_URI = 'mongodb+srv://korisnik:korisnik123@cluster0.cie9x.mongodb.net/tablicaaa?retryWrites=true&w=majority&appName=Cluster0';

mongoose.connect(MONGO_URI)
  .then(() => console.log('Connected to MongoDB'))
  .catch(err => console.error('Error connecting to MongoDB:', err));

async function addUser() {
  try {
    const user = new User({
      username: 'Josip',
      password: 'Josip123',
      name: 'Josip',
      email: 'Josip1',
      role: 'admin',
      place: 'Gerovo',
      postalCode: '51304',
      street: 'Ognjisce',
      phone: '123456789',
    });

    await user.save();
    console.log('User added successfully!');
  } catch (error) {
    console.error('Error adding user:', error);
  }
}

async function addProduct() {
  try {
    const category = await Category.findOne();
    const product = new Product({
      name: 'Croissant',
      description: 'Delicious butter croissant',
      price: 0.8,
      stock: 100,
      category: category._id,
      image: 'path/to/croissant.jpg',
    });

    await product.save();
    console.log('Product added successfully!');
  } catch (error) {
    console.error('Error adding product:', error);
  }
}

async function addOrder() {
  try {
    const user = await User.findOne();
    const product = await Product.findOne();

    const order = new Order({
      userId: user._id,
      products: [
        {
          productId: product._id,
          quantity: 1,
        },
      ],
      totalAmount: product.price,
      status: 'pending',
    });

    await order.save();
    console.log('Order added successfully!');
  } catch (error) {
    console.error('Error adding order:', error);
  }
}

async function addCategory() {
  try {
    const category = new Category({
      name: 'Pastries',
      description: 'All kinds of delicious pastries',
    });

    await category.save();
    console.log('Category added successfully!');
  } catch (error) {
    console.error('Error adding category:', error);
  }
}

async function addCart() {
  try {
    const user = await User.findOne();
    const product = await Product.findOne();

    const cart = new Cart({
      userId: user._id,
      cartItems: [
        {
          productId: product._id,
          name: product.name,
          price: product.price,
          quantity: 1,
        },
      ],
    });

    await cart.save();
    console.log('Cart added successfully!');
  } catch (error) {
    console.error('Error adding cart:', error);
  }
}

async function addData() {
  await addCategory();
  await addUser();
  await addProduct();
  await addOrder();
  await addCart();

  mongoose.disconnect();
}

addData();

