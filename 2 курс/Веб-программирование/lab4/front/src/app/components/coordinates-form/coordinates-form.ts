import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonInfoService} from '../../services/common-info-service';
import {PointsTableComponent} from '../points-table/points-table';
import {PointRequest} from '../../dto/PointRequest';
import {DataService} from '../../services/data-service';
import {Point} from '../../models/Point';
import {PointResponse} from '../../dto/PointResponse';
import {Subscription} from 'rxjs';

@Component({
  selector: "app-coordinates-form",
  imports: [FormsModule],
  templateUrl: "coordinates-form.html",
  styleUrl: "coordinates-form.css",
  standalone: true
})
export class CoordinatesFormComponent implements OnInit{
  constructor(private common: CommonInfoService, private dataService: DataService) {
  }

  ngOnInit(): void {
    this.rSelect = localStorage.getItem("r_value") === null ? localStorage.getItem("r_value") : ""
  }
  xSelect = ""
  yInput = ""
  rSelect: string | null = ""
  buttonClicked: boolean = false
  // @Output() buttonClicked = new EventEmitter<any>();
  errorMessage = ""
  // point: Point = new PointResponse()

  // private pointsSubscription?: Subscription;
  // private pointAddedSubscription?: Subscription;

  submitForm(): void {
    this.buttonClicked = true
    this.common.r = Number(this.rSelect)
    console.log('submitForm')
    this.dataService.sendPoint(new PointRequest(this.xSelect, this.yInput, this.rSelect)).subscribe({
      next: (response) => {
        // const newPoint = response.getPoint();
        // this.common.addPoint(newPoint);
        const newPoint = new Point(response.x, response.y, response.r, response.status, response.currentTime, response.executionTime);
        console.log(`CoordinatesFormComponent: responsePoint_newPoint(${newPoint.x}, ${newPoint.y}, ${newPoint.r}, ${newPoint.status}, ${newPoint.currentTime}, ${newPoint.executionTime})`)
        this.common.addPoint(newPoint);
      },
      error: () => {
        this.errorMessage = "err"
      }
    })


    setTimeout(() => {
      this.buttonClicked = false
    }, 3000)
  }

  changeR() {
    this.common.r = Number(this.rSelect)
  }

  protected readonly Number = Number;
}
