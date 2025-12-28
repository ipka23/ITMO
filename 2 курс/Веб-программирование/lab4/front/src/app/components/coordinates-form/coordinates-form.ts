import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonInfoService} from '../../services/common-info-service';

@Component({
  selector: "app-coordinates-form",
  imports: [FormsModule],
  templateUrl: "coordinates-form.html",
  standalone: true
})
export class CoordinatesFormComponent {
  constructor(private common: CommonInfoService) {
  }
  xSelect = ""
  yInput = ""
  rSelect = ""
  buttonClicked: boolean = false
  submitForm(): void {
    this.buttonClicked = true
    this.common.r = +this.rSelect

    setTimeout(() => {
      this.buttonClicked = false
    }, 3000)
  }
}
