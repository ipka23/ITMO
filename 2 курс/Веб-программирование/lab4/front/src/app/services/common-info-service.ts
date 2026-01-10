import { Injectable } from '@angular/core';
import {Point} from '../models/Point';
import {BehaviorSubject, Observable} from 'rxjs';
import {PointRequest} from '../dto/PointRequest';

@Injectable({
  providedIn: 'root'
})
export class CommonInfoService {
  private _userId = 0;
  private _points: Point[] | undefined
  private readonly R_KEY = 'r_value';
  private readonly USER_ID_KEY = 'user_id';
  private readonly POINT_KEY = 'current_point';
  private readonly POINTS_KEY = 'points_collection';


  private tableLoadedSubject = new BehaviorSubject<boolean>(false)
  private newPointSubject = new BehaviorSubject<Point | null>(null)
  private pointsSubject = new BehaviorSubject<Point[] | null>([])
  private radius = new BehaviorSubject<number>(-1)

  tableLoaded$: Observable<boolean> = this.tableLoadedSubject.asObservable()
  newPoint$ = this.newPointSubject.asObservable()
  points$ = this.pointsSubject.asObservable()
  r$ = this.radius.asObservable()


  get r(): number {
    const saved = localStorage.getItem(this.R_KEY);
    return saved ? +saved : -1;
  }

  get userId(): number {
    const saved = localStorage.getItem('user_id');
    return saved !== null ? +saved : this._userId;
  }

  set r(value: number) {
    if (value !== this.r) {
      localStorage.setItem(this.R_KEY, value.toString());
      this.radius.next(value);
    }
  }


  set userId(value: number) {
    if (value !== this.userId) {
      localStorage.setItem(this.USER_ID_KEY, value.toString());
    }
  }


  get points(): Point[] | undefined {
    // return this.pointsSubject.value;
  return this._points
  }

  setPoints(points: Point[]): void {
    // this.pointsSubject.next(points);
    this._points = points

  }
  addPoint(point: Point) {

    let updatedPoints = this._points
    if (this._points != undefined) {
      updatedPoints = [point, ...this._points]
      this.pointsSubject.next(updatedPoints);
    }


    this.newPointSubject.next(point);
  }



  updatePoints() {

  }
}

























// addPoint(point: Point): void {
//   const currentPoints = this.pointsSubject.value;
//
//   const updatedPoints = [point, ...currentPoints];
//   //
//   // this.pointsSubject.next(updatedPoints);
//   //
//   // this.pointAddedSubject.next(point);
//
// }
//

//
//
// get point(): Point {
//   const saved = localStorage.getItem(this.POINT_KEY);
//   if (saved) {
//     try {
//       const parsed = JSON.parse(saved);
//       return new Point(
//         parsed.x,
//         parsed.y,
//         parsed.r,
//         parsed.result,
//         parsed.curTime,
//         parsed.execTime
//       );
//     } catch (e) {
//       console.error('Error parsing point from localStorage:', e);
//     }
//   }
//   return this.defaultPoint;
// }
//
// set point(point: Point) {
//   try {
//     const pointData = {
//       x: point.x,
//       y: point.y,
//       r: point.r,
//       result: point.status,
//       curTime: point.currentTime,
//       execTime: point.executionTime
//     };
//     localStorage.setItem(this.POINT_KEY, JSON.stringify(pointData));
//   } catch (e) {
//     console.error('Error saving point to localStorage:', e);
//   }
// }
//
//
// private loadPoints(): Point[] {
//   const saved = localStorage.getItem(this.POINTS_KEY);
//   if (saved) {
//     try {
//       const parsed = JSON.parse(saved);
//       if (Array.isArray(parsed)) {
//         return parsed.map((p: any) => new Point(
//           p.x || "0",
//           p.y || "0",
//           p.r || "0",
//           p.status || "0",
//           p.currentTime || "0",
//           p.executionTime || "0"
//         ));
//       }
//     } catch (e) {
//       console.error('Err: ', e);
//     }
//   }
//   return [...this.defaultPoints];
// }
//
// private savePoints(points: Point[]): void {
//   try {
//     const pointsData = points.map(p => ({
//       x: p.x,
//       y: p.y,
//       r: p.r,
//       result: p.status,
//       curTime: p.currentTime,
//       execTime: p.executionTime
//     }));
//     localStorage.setItem(this.POINTS_KEY, JSON.stringify(pointsData));
//   } catch (e) {
//     console.error('Error saving points to localStorage:', e);
//   }
// }
//
//
//
// updatePoints() {
//   // this.svgGraph.refreshPoints()
// }
