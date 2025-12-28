import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CommonInfoService {
  private _r = -1;
  private _userId = 0;

  get r(): number {
    return this._r;
  }

  get userId(): number {
    return this._userId;
  }

  set r(value: number) {
    this._r = value;
  }

  set userId(value: number) {
    this._userId = value;
  }
}
