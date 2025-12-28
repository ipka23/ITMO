import {Component, EventEmitter, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonInfoService} from '../../services/common-info-service';
import {PointsTableComponent} from '../points-table/points-table';
import {PointRequest} from '../../dto/PointRequest';
import {DataService} from '../../services/data-service';
import {Point} from '../../models/Point';
import {PointResponse} from '../../dto/PointResponse';

@Component({
  selector: "app-coordinates-form",
  imports: [FormsModule],
  templateUrl: "coordinates-form.html",
  styleUrl: "coordinates-form.css",
  standalone: true
})
export class CoordinatesFormComponent {
  constructor(private common: CommonInfoService, private dataService: DataService) {
  }
  xSelect = ""
  yInput = ""
  rSelect = ""
  buttonClicked: boolean = false
  // @Output() buttonClicked = new EventEmitter<any>();
  errorMessage = ""
  // point: Point = new PointResponse()
  submitForm(): void {
    this.buttonClicked = true
    this.common.r = +this.rSelect

    this.dataService.sendPoint(new PointRequest(this.xSelect, this.yInput, this.rSelect)).subscribe({
      next: (response) => {
        this.common.point = response.getPoint()
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
    if (+this.rSelect < 0) {}
    this.common.r = +this.rSelect
  }
}
