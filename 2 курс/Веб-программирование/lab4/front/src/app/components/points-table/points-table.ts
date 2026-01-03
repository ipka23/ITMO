import {Component, OnInit} from "@angular/core";
import {Point} from '../../models/Point';
import {PointService} from '../../services/point-service';
import {LoggerService} from '../../services/logger-service';
import {DataService} from '../../services/data-service';
import {PointRequest} from '../../dto/PointRequest';
import {reduce} from 'rxjs';
import {CommonInfoService} from '../../services/common-info-service';
import {PointResponse} from '../../dto/PointResponse';
import {FormsModule} from '@angular/forms';
import {SvgGraphComponent} from '../svg-graph/svg-graph';


@Component({
  selector: "app-points-table",
  providers: [PointService, LoggerService, SvgGraphComponent],
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
  constructor(private dataService: DataService, private common: CommonInfoService, private svgGraph: SvgGraphComponent) {}

  ngOnInit() {

    // console.log(this.points)
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
  }


  private loadPointsFromServer(): void {
    this.dataService.getPoints().subscribe({
      next: (response) => {
        const points = response.points || response;
        console.log(response.points)
        this.points = response.points
        this.common.setPoints(points);
        this.svgGraph.refreshPoints()

      },
      error: (err) => {
        this.errorMessage = "serverErr: " + err.error
        console.log( "serverErr: " + err.error)
      }
    });
  }
}
