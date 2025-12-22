import {Component, OnInit} from "@angular/core";
import {Point} from './utility/Point';
import {PointsService} from './services/points.service';
import {LoggerService} from './services/logger.service';


@Component({
  selector: "app-points-table",
  providers: [PointsService, LoggerService],
  template:
    `
      <div>
        <table>
          <thead>
          <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Попадание/промах</th>
            <th>Текущее время</th>
            <th>Время выполнения</th>
          </tr>
          </thead>
          @for (point of points; track point) {
            <tbody>
            <td>point.x</td>
            <td>point.y</td>
            <td>point.r</td>
            <td>point.result</td>
            <td>point.currentTime</td>
            <td>point.executionTime</td>
            </tbody>
          }
        </table>
      </div>
    `,
  // styles: ``
})

export class PointsTableComponent implements OnInit {
  constructor(private dataPointsService: PointsService, private points: Point[]) {}

  ngOnInit() : void {
    this.points = this.dataPointsService.getPoints()
  }

}
