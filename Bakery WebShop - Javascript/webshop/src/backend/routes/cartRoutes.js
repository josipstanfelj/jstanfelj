const express = require("express");
const mongoose = require("mongoose");
const Cart = require("../models/cart");
const router = express.Router();

router.get("/:userId", async (req, res) => {
  try {
    console.log("Početak dohvaćanja košarice");

    let cart = await Cart.findOne({ userId: req.params.userId });
    console.log("Košarica pronađena:", cart);

    if (!cart) {
      console.log("Košarica ne postoji, stvaram novu...");
      cart = new Cart({ userId: req.params.userId, cartItems: [] });
      await cart.save();
      console.log("Nova košarica spremljena:", cart);
    }

    if (!cart.cartItems) {
      cart.cartItems = [];
    }

    res.json(cart);
    console.log("Uspješno dohvacena košarica");
  } catch (error) {
    console.error("Greška pri dohvaćanju košarice:", error);
    res.status(500).json({ message: "Greška pri dohvaćanju košarice", error });
  }
});




router.post("/:userId", async (req, res) => {
  try {
    const { userId } = req.params;
    const { cartItems } = req.body;

    if (!cartItems || !Array.isArray(cartItems)) {
      return res.status(400).json({ message: "Neispravan format za stavke košarice." });
    }

    let cart = await Cart.findOne({ userId });

    if (!cart) {
      cart = new Cart({ userId, cartItems });
    } else {
      cart.cartItems = cartItems;
    }

    await cart.save();
    res.json(cart);
  } catch (error) {
    console.error("Greška pri spremanju košarice:", error);
    res.status(500).json({ message: "Greška pri spremanju košarice", error });
  }
});

module.exports = router;


