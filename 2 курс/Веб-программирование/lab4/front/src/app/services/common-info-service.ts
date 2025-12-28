import { Injectable } from '@angular/core';
import {Point} from '../models/Point';
import {BehaviorSubject, Observable, pipe} from 'rxjs';
import {PointResponse} from '../dto/PointResponse';

@Injectable({
  providedIn: 'root'
})
export class CommonInfoService {
  private _r = -1;
  private _userId = 0;
  private _point = new Point("0", "0", "0", "0", "0", "0")

  private pointsSubject = new BehaviorSubject<Point[]>([]);
  private pointAddedSubject = new BehaviorSubject<Point | null>(null);
  points$: Observable<Point[]> = this.pointsSubject.asObservable();
  pointAdded$: Observable<Point | null> = this.pointAddedSubject.asObservable();

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

  get points(): Point[] {
    return this.pointsSubject.value;
  }

  addPoint(point: Point): void {
    const currentPoints = this.pointsSubject.value;

    const updatedPoints = [point, ...currentPoints];

    this.pointsSubject.next(updatedPoints);

    this.pointAddedSubject.next(point);

  }

  setPoints(points: Point[]): void {
    this.pointsSubject.next(points);
  }
}
