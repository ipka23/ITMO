import {Component, OnInit} from '@angular/core';
import {HttpBackend, HttpClient} from '@angular/common/http';
import {PointService} from '../../services/point-service';
import {CoordinatesFormComponent} from '../coordinates-form/coordinates-form';
import {CommonInfoService} from '../../services/common-info-service';
import {FormsModule} from '@angular/forms';
import {PointRequest} from '../../dto/PointRequest';
import {NgStyle} from '@angular/common';

@Component({
  selector: 'app-svg-graph',
  imports: [
    FormsModule,
    NgStyle
  ],
  templateUrl: './svg-graph.html',
  styleUrl: './svg-graph.css',
})
export class SvgGraphComponent {

  constructor(protected common: CommonInfoService, private pointService: PointService) {
  }

  protected svgError = ""


  svgClick($event: PointerEvent) {
    if (this.r == 0 || this.r.toString() == "") {
      this.svgError = "Выберите радиус R!"
    }
    this.pointService.drawPointByClick($event)
  }

  get r(): number {
    return this.common.r;

  }


  showError() {
    if (this.r == 0 || this.r.toString() == "") {
      return {
        'visibility': 'visible'
      }
    } else {
      return {
        'visibility': 'hidden'
      }
    }
  }
}
