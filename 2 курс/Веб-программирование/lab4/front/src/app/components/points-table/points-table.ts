import {Component, OnInit} from "@angular/core";
import {Point} from '../../models/Point';
import {PointService} from '../../services/point-service';
import {LoggerService} from '../../services/logger-service';
import {DataService} from '../../services/data-service';
import {PointRequest} from '../../dto/PointRequest';
import {CommonInfoService} from '../../services/common-info-service';
import {FormsModule} from '@angular/forms';
import {SyncService} from '../../services/sync-service';
import {BehaviorSubject} from 'rxjs';


@Component({
  selector: "app-points-table",
  providers: [PointService, LoggerService, SyncService],
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
  constructor(private dataService: DataService, private common: CommonInfoService, private syncService: SyncService) {}
  tableLoaded = new BehaviorSubject(false)
  ngOnInit() {
    this.loadPointsFromServer()
    this.dataService.getPoints().subscribe({
      next: (response) => {
          this.points.unshift(new Point(response.x, response.y, response.r, response.status, response.currentTime, response.executionTime));
      },
      error: (err) => {
        this.errorMessage = "serverErr: " + err.error
        console.log( "serverErr: " + err.error)
      }
    })
    this.tableLoaded = new BehaviorSubject(true)
    this.syncService.tableLoaded$ = this.tableLoaded

  }

  addPoint(point: PointRequest) {
    this.dataService.sendPoint(point).subscribe({
      next: (response) => {
        this.points.unshift(new Point(response.x, response.y, response.r, response.status, response.currentTime, response.executionTime));
      },
      error: (err) => {
        this.errorMessage = "serverErr: " + err.error
        console.log( "serverErr: " + err.error)
      }
    })
    this.syncService.pointAdded$ = this.pointAdded
  }




  private loadPointsFromServer(): void {
    this.dataService.getPoints().subscribe({
      next: (response) => {
        const points = response.points || response;
        console.log(response.points)
        this.points = response.points
        console.log("response.points(PointsTableComponent): " + this.points)

        this.common.setPoints(points)
        this.common.updatePoints()
        // this.svgGraph.refreshPoints()

      },
      error: (err) => {
        this.errorMessage = "serverErr: " + err.error
        console.log( "serverErr: " + err.error)
      }
    });
  }
}
