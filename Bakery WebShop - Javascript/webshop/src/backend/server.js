require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const bodyParser = require('body-parser');

const app = express();

app.use(cors());
app.use(bodyParser.json());
app.use('/uploads', express.static('uploads'));


mongoose.connect('mongodb+srv://korisnik:korisnik123@cluster0.cie9x.mongodb.net/pekarnicashop?retryWrites=true&w=majority&appName=Cluster0')
  .then(() => console.log("Connected to MongoDB database"))
  .catch(err => console.log(err));

const productRoutes = require('./routes/productRoutes');
const orderRoutes = require('./routes/orderRoutes');
const categoryRoutes = require('./routes/categoryRoutes');
const authRoutes = require('./routes/auth');
const cartRoutes = require('./routes/cartRoutes');
const uploadRoutes = require('./routes/uploadRoutes');


app.use('/api/products', productRoutes);
app.use('/api/orders', orderRoutes);
app.use('/api/categories', categoryRoutes);
app.use('/api/auth', authRoutes);
app.use('/api/cart',cartRoutes);
app.use('/api/upload', uploadRoutes);


const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
