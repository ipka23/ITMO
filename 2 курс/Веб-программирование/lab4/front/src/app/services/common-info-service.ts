import { Injectable } from '@angular/core';
import {Point} from '../models/Point';
import {pipe} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommonInfoService {
  private _r = -1;
  private _userId = 0;
  private _point = new Point("0", "0", "0", "0", "0", "0")
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

  set point(point: Point) {
    this._point = point
  }
}
