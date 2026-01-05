import {Injectable, OnInit} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SyncService {
  constructor(private _tableLoaded: BehaviorSubject<boolean>) {
  }

  set tableLoaded(behaviorSubject: BehaviorSubject<boolean>) {
    this._tableLoaded = behaviorSubject
  }

  // ngOnInit() {
  //   // this._tableLoaded.asObservable().subscribe(isReady => {
  //   //
  //   // })
  // }

}
