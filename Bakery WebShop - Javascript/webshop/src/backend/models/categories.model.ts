export class Category {
  _id?: string;
  name: string;
  description?: string;

  constructor(name: string, description?: string, _id?: string) {
    this.name = name;
    this.description = description;
    this._id = _id;
  }
}
