const express = require("express");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const User = require("../models/users");
const authenticateToken = require("../token/token");

const router = express.Router();

router.post("/register", async (req, res) => {
  const { username, password, name, email } = req.body;

  if (!username || !password || !name || !email) {
    return res.status(400).json({ message: "Svi podaci su obavezni" });
  }

  try {
    const userExists = await User.findOne({ username });
    if (userExists) {
      return res.status(400).json({ message: "Korisničko ime već postoji" });
    }

    const hashedPassword = await bcrypt.hash(password, 10);
    const newUser = new User({ username, password: hashedPassword, name, email });
    await newUser.save();

    const token = jwt.sign({ id: newUser._id }, process.env.JWT_SECRET, { expiresIn: "1h" });

    res.status(201).json({
      message: "Korisnik je registriran",
      user: {
        id: newUser._id,
        username: newUser.username,
        name: newUser.name,
        email: newUser.email
      },
      token: token
    });
  } catch (err) {
    res.status(500).json({ message: "Greška pri registraciji" });
  }
});

router.post("/login", async (req, res) => {
  const { username, password } = req.body;

  if (!username || !password) {
    return res.status(400).json({ message: "Svi podaci su obavezni" });
  }

  try {
    const user = await User.findOne({ username });
    if (!user) {
      return res.status(400).json({ message: "Pogrešno korisničko ime" });
    }

    const isMatch = await bcrypt.compare(password, user.password);
    if (!isMatch) {
      return res.status(400).json({ message: "Pogrešna lozinka" });
    }

    const token = jwt.sign({ id: user._id }, process.env.JWT_SECRET, { expiresIn: "1h" });

    res.json({ token, user: { id: user._id, username: user.username, name: user.name, email: user.email } });
  } catch (err) {
    res.status(500).json({ message: "Greška pri prijavi" });
  }
});

router.get("/:userId", authenticateToken, async (req, res) => { // Dodan middleware za autentifikaciju
  try {
    if (req.userId !== req.params.userId) {
      return res.status(403).json({ message: "Nemate pristup podacima ovog korisnika" });
    }

    const user = await User.findById(req.params.userId);
    if (!user) {
      return res.status(404).json({ message: "Korisnik ne postoji" });
    }
    res.json(user);
  } catch (err) {
    res.status(500).json({ message: "Greška pri dohvaćanju korisnika" });
  }
});

router.get("/", authenticateToken, async (req, res) => {
  try {
    const user = await User.findById(req.userId);
    if (!user || user.role !== "admin") {
      return res.status(403).json({ message: "Nemate pristup ovoj ruti" });
    }
    const users = await User.find({}, "_id username name email role");
    res.json({ users });
  } catch (err) {
    res.status(500).json({ message: "Greška pri dohvaćanju korisnika" });
  }
});

module.exports = router;


router.delete("/:userId", authenticateToken, async (req, res) => {


  try {
    const user = await User.findById(req.userId);

    if (user.role !== "admin") {
      return res.status(403).json({ message: "Pristup odbijen. Samo admin može obrisati korisnika." });
    }

    const deleteUser = await User.findByIdAndDelete(req.params.userId);
    if (!deleteUser) {
      return res.status(404).json({ message: "Korisnik ne postoji." });
    }

    res.json({ message: "Korisnik uspješno obrisan." });
  } catch (err) {
    res.status(500).json({ message: "Greška pri brisanju korisnika." });
  }
});

router.put("/:userId", authenticateToken, async (req, res) => {
  if (req.userId !== req.params.userId) {
    return res.status(403).json({ message: "Nemate dozvolu za ažuriranje ovog korisnika." });
  }

  const { name, email, place, postalCode, street, phone } = req.body;

  try {
    const user = await User.findById(req.params.userId);

    if (!user) {
      return res.status(404).json({ message: "Korisnik ne postoji." });
    }

    user.name = name || user.name;
    user.email = email || user.email;

    if (place !== undefined) user.place = place;
    if (postalCode !== undefined) user.postalCode = postalCode;
    if (street !== undefined) user.street = street;
    if (phone !== undefined) user.phone = phone;

    await user.save();

    res.json({ message: "Podaci su uspješno ažurirani.", user });
  } catch (err) {
    res.status(500).json({ message: "Greška pri ažuriranju podataka." });
  }
});






module.exports = router;
