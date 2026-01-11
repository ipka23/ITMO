import {ChangeDetectorRef, Component, OnInit} from "@angular/core";
import {Point} from '../../models/Point';
import {PointService} from '../../services/point-service';
import {LoggerService} from '../../services/logger-service';
import {DataService} from '../../services/data-service';
import {CommonInfoService} from '../../services/common-info-service';
import {FormsModule} from '@angular/forms';
import {BehaviorSubject} from 'rxjs';


@Component({
  selector: "app-points-table",
  providers: [PointService, LoggerService],
  templateUrl: "points-table.html",
  standalone: true,
  imports: [
    FormsModule
  ],
  styleUrl: "points-table.css"
})

export class PointsTableComponent implements OnInit {
  public points: Point[] = []
  errorMessage: string = ""

  constructor(private dataService: DataService, private common: CommonInfoService, private cdr: ChangeDetectorRef) {
  }

  tableLoaded = new BehaviorSubject(false)

  ngOnInit() {
    this.loadPointsFromServer()
    this.common.newPoint$.subscribe(point => {
      if (point) {
        this.points.unshift(point)
        this.cdr.detectChanges();
        console.log(`PointsTableComponent Point(${point?.x}, ${point?.y}, ${point?.r}, ${point?.status}, ${point?.currentTime}, ${point?.executionTime})`)
      }
    })

  }


  private loadPointsFromServer(): void {
    this.dataService.getPoints().subscribe({
      next: (response) => {
        let points: Point[] = []
        if (response.points === undefined) {
          this.points = points
        } else {
          this.points = response.points.reverse()
        }

        this.common.setPoints(this.points)
        this.tableLoaded.next(true)
        this.common.tableLoaded$ = this.tableLoaded
      },
      error: (err) => {
        this.errorMessage = "serverErr: " + err.error
        console.log("serverErr: " + err.error)
      }
    });
  }
}
