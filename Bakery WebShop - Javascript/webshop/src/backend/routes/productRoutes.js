const express = require('express');
const Product = require('../models/products'); // Uvoz modela proizvoda
const router = express.Router();

router.post('/', async (req, res) => {
  try {
    const { name, description, price, stock, category, image } = req.body;
    const newProduct = new Product({ name, description, price, stock, category, image });

    await newProduct.save();
    res.status(201).json(newProduct);
  } catch (error) {
    res.status(500).json({ message: 'Došlo je do pogreške pri dodavanju proizvoda', error });
  }
});

router.get('/', async (req, res) => {
  try {
    const products = await Product.find().populate('category');
    res.status(200).json(products);
  } catch (error) {
    res.status(500).json({ message: 'Došlo je do pogreške', error });
  }
});

router.get('/:id', async (req, res) => {
  try {
    const product = await Product.findById(req.params.id).populate('category');
    if (!product) {
      return res.status(404).json({ message: 'Proizvod nije pronađen' });
    }
    res.status(200).json(product);
  } catch (error) {
    res.status(500).json({ message: 'Došlo je do pogreške', error });
  }
});
router.put("/:id", async (req, res) => {
  const { id } = req.params;
  const { name, description, price, stock, category, image } = req.body;
  try {
    const updatedProduct = await Product.findByIdAndUpdate(id, { name, description, price, stock, category, image }, { new: true });
    if (!updatedProduct) {
      return res.status(404).json({ message: "Proizvod nije pronađen" });
    }
    res.json(updatedProduct);
  } catch (err) {
    res.status(400).json({ message: "Greška pri ažuriranju proizvoda" });
  }
});


router.delete("/:id", async (req, res) => {
  const { id } = req.params;
  try {
    const deletedProduct = await Product.findByIdAndDelete(id);
    if (!deletedProduct) {
      return res.status(404).json({ message: "Proizvod nije pronađen" });
    }
    res.json({ message: "Proizvod uspješno obrisan" });
  } catch (err) {
    res.status(500).json({ message: "Greška pri brisanju proizvoda" });
  }
});

module.exports = router;

