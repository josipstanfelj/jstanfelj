const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
  username: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  name: { type: String, required: true },
  email: { type: String, required: true, unique: true },
  role: {
    type: String,
    enum: ['admin', 'customer', 'moderator'],
    default: 'customer',
  },

  place: { type: String, default: '' },
  postalCode: { type: String, default: '' },
  street: { type: String, default: '' },
  phone: { type: String, default: '' },

  createdAt: { type: Date, default: Date.now }
});

const User = mongoose.model('User', userSchema);
module.exports = User;
