import {Component, OnInit} from "@angular/core";
import {Point} from '../../models/Point';
import {PointService} from '../../services/point-service';
import {LoggerService} from '../../services/logger-service';
import {DataService} from '../../services/data-service';
import {PointRequest} from '../../dto/PointRequest';
import {reduce} from 'rxjs';
import {CommonInfoService} from '../../services/common-info-service';
import {PointResponse} from '../../dto/PointResponse';


@Component({
  selector: "app-points-table",
  providers: [PointService, LoggerService],
  templateUrl: "points-table.html",
  styleUrl: "points-table.css"
})

export class PointsTableComponent implements OnInit {
  public points: Point[] = []
  errorMessage: string = ""
  constructor(private dataService: DataService, private common: CommonInfoService) {}

  ngOnInit() {
    this.loadPointsFromServer()
    this.dataService.getPoints().subscribe({
      next: (response) => {
          this.points.unshift(response.getPoint());
      },
      error: () => {
        this.errorMessage = "serverErr"
      }
    })
  }

  addPoint(point: PointRequest) {
    this.dataService.sendPoint(point).subscribe({
      next: (response) => {
        this.points.unshift(response.getPoint())
      },
      error: () => {
        this.errorMessage = "serverErr"
      }
    })
  }


  private loadPointsFromServer(): void {
    this.dataService.getPoints().subscribe({
      next: (response) => {
        const points = response.points || response;

        this.common.setPoints(points);

      },
      error: () => {
        this.errorMessage = "Ошибка загрузки данных";
      }
    });
  }

}
