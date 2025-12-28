import {Point} from '../models/Point';

export class PointResponse {
  private _points: Point[] = [];
  constructor(public x: string, public y: string, public r: string, public status: string, public currentTime: string, public executionTime: string) {
  }
  getPoint(response: PointResponse): Point {
    return new Point(response.x, response.y, response.r, response.status, response.currentTime, response.executionTime)
  }

  set points(points: Point[]) {
    this._points = points
  }

  get points() {
   return this._points
  }
}
