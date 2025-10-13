export class User {
  _id: string;
  username: string;
  password?: string;
  name: string;
  email: string;
  role: 'admin' | 'customer' | 'moderator';
  place?: string;
  postalCode?: string;
  street?: string;
  phone?: string;

  constructor(
    _id: string = '',
    username: string = '',
    password: string = '',
    name: string = '',
    email: string = '',
    role: 'admin' | 'customer' | 'moderator' = 'customer',
    place: string = '',
    postalCode: string = '',
    street: string = '',
    phone: string = ''
  ) {
    this._id = _id;
    this.username = username;
    this.password = password;
    this.name = name;
    this.email = email;
    this.role = role;
    this.place = place;
    this.postalCode = postalCode;
    this.street = street;
    this.phone = phone;
  }
}
