import {Point} from '../models/Point';

export class PointResponse {
  private _points: Point[] = [];
  constructor(public x: string, public y: string, public r: string, public status: string, public currentTime: string, public executionTime: string) {
  }
  getPoint(): Point {
    const point = new Point(this.x, this.y, this.r, this.status, this.currentTime, this.executionTime)
    console.log(`PointResponse getPoint(): Point(${this.x}, ${this.y}, ${this.r}, ${this.status}, ${this.currentTime}, ${this.executionTime})`)

    return point
  }

  set points(points: Point[]) {
    this._points = points
  }

  get points() {
   return this._points
  }
}
