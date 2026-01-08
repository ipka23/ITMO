import {Injectable, OnInit} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SyncService {

  private tableLoadedSubject = new BehaviorSubject<boolean>(false);
  private pointAddedSubject = new BehaviorSubject<boolean>(false);
  tableLoaded$: Observable<boolean> = this.tableLoadedSubject.asObservable();

  // setTableLoaded(value: boolean): void {
  //   this.tableLoadedSubject.next(value);
  // }



  pointAdded$: Observable<boolean> = this.tableLoadedSubject.asObservable();

  // setPointLoaded(value: boolean): void {
  //   this.tableLoadedSubject.next(value);
  // }
}
