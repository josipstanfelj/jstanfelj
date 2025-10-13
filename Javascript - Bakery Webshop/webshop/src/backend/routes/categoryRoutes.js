const express = require('express');
const Category = require('../models/categories');
const router = express.Router();

router.post('/', async (req, res) => {
  try {
    const { name, description } = req.body;
    const newCategory = new Category({ name, description });
    await newCategory.save();
    res.status(201).json(newCategory);
  } catch (error) {
    res.status(500).json({ message: 'Došlo je do pogreške pri dodavanju kategorije', error });
  }
});

router.put("/:id", async (req, res) => {
  const { id } = req.params;
  const { name, description } = req.body;
  try {
    const updatedCategory = await Category.findByIdAndUpdate(id, { name, description }, { new: true });
    if (!updatedCategory) {
      return res.status(404).json({ message: "Kategorija nije pronađena" });
    }
    res.json(updatedCategory);
  } catch (err) {
    res.status(400).json({ message: "Greška pri ažuriranju kategorije" });
  }
});

router.get('/', async (req, res) => {
  try {
    const categories = await Category.find();
    res.status(200).json(categories);
  } catch (error) {
    res.status(500).json({ message: 'Došlo je do pogreške', error });
  }
});

router.get('/:id', async (req, res) => {
  try {
    const category = await Category.findById(req.params.id);
    if (!category) {
      return res.status(404).json({ message: 'Kategorija nije pronađena' });
    }
    res.status(200).json(category);
  } catch (error) {
    res.status(500).json({ message: 'Došlo je do pogreške', error });
  }
});

router.delete("/:id", async (req, res) => {
  const { id } = req.params;
  try {
    const deletedCategory = await Category.findByIdAndDelete(id);

    res.json({ message: "Kategorija uspješno obrisana" });
  } catch (err) {
    res.status(500).json({ message: "Greška pri brisanju kategorije" });
  }
});

module.exports = router;
