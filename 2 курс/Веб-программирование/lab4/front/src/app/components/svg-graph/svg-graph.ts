import { Component } from '@angular/core';
import {HttpBackend, HttpClient} from '@angular/common/http';
import {PointService} from '../../services/point-service';
import {CoordinatesFormComponent} from '../coordinates-form/coordinates-form';
import {CommonInfoService} from '../../services/common-info-service';
import {FormsModule} from '@angular/forms';
import {PointRequest} from '../../dto/PointRequest';

@Component({
  selector: 'app-svg-graph',
  imports: [
    FormsModule
  ],
  templateUrl: './svg-graph.html',
  styleUrl: './svg-graph.css',
})
export class SvgGraphComponent {
  protected svgError = ""
  constructor(private http: HttpClient, private common: CommonInfoService, private pointService: PointService) { //, private pointService: PointService
  }

  svgClick($event: PointerEvent) {
    // try {
      this.pointService.drawPointByClick($event)
    // }
    // catch () {
    //
    // }
  }




}
