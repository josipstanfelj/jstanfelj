const express = require('express');
const Order = require('../models/orders'); // Uvoz modela narudžbe
const router = express.Router();

router.post('/', async (req, res) => {
  try {
    const { userId, products, totalAmount } = req.body;
    const newOrder = new Order({ userId, products, totalAmount });
    await newOrder.save();
    res.status(201).json(newOrder);
  } catch (error) {
    res.status(500).json({ message: 'Došlo je do pogreške pri stvaranju narudžbe', error });
  }
});

router.get('/', async (req, res) => {
  try {
    const orders = await Order.find().populate('userId').populate('products.productId');
    res.status(200).json(orders);
  } catch (error) {
    res.status(500).json({ message: 'Došlo je do pogreške', error });
  }
});

router.get('/:id', async (req, res) => {
  try {
    const order = await Order.findById(req.params.id).populate('userId').populate('products.productId');
    if (!order) {
      return res.status(404).json({ message: 'Narudžba nije pronađena' });
    }
    res.status(200).json(order);
  } catch (error) {
    res.status(500).json({ message: 'Došlo je do pogreške', error });
  }
});

router.get('/user/:userId', async (req, res) => {
  try {
    const { userId } = req.params;
    const orders = await Order.find({ userId }).populate('products.productId');
    res.status(200).json(orders);
  } catch (error) {
    res.status(500).json({ message: 'Došlo je do pogreške pri dohvaćanju narudžbi', error });
  }
});

router.delete('/:id', async (req, res) => {
  try {
    const orderId = req.params.id;
    const deletedOrder = await Order.findByIdAndDelete(orderId);

    if (!deletedOrder) {
      return res.status(404).json({ message: 'Narudžba nije pronađena' });
    }

    res.status(200).json({ message: 'Narudžba je uspješno obrisana', order: deletedOrder });
  } catch (error) {
    res.status(500).json({ message: 'Došlo je do pogreške pri brisanju narudžbe', error });
  }
});



module.exports = router;
