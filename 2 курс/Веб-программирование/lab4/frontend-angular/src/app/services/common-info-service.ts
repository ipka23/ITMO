import {Injectable} from '@angular/core';
import {Point} from '../models/Point';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommonInfoService {
  private _userId = 0;
  private _points: Point[] | undefined
  private readonly R_KEY = 'r_value'
  private readonly USER_ID_KEY = 'user_id'
  private JWT = ""

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
  get jwt(): string {
    return this.JWT
  }
  set jwt(value: string) {
    this.JWT = value
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
    return this._points
  }

  setPoints(points: Point[]): void {
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

}
