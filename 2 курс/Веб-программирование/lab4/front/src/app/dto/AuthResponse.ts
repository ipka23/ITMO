export class AuthResponse {
  constructor(
    private _isValid: boolean,
    private _message: string,
    private _id: string
  ) {}

  get isValid(): boolean {
    return this._isValid;
  }

  get message(): string {
    return this._message;
  }

  get id(): string {
    return this._id;
  }

  set isValid(value: boolean) {
    this._isValid = value;
  }

  set message(value: string) {
    this._message = value;
  }

  set id(value: string) {
    this._id = value;
  }
}
