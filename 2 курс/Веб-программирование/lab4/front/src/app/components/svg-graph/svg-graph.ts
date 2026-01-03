import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {PointService} from '../../services/point-service';
import {CommonInfoService} from '../../services/common-info-service';
import {FormsModule} from '@angular/forms';
import {NgStyle} from '@angular/common';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-svg-graph',
  imports: [
    FormsModule,
    NgStyle,
    CommonModule
  ],
  templateUrl: './svg-graph.html',
  styleUrl: './svg-graph.css',
  standalone: true
})
export class SvgGraphComponent implements OnChanges {
  rPxWidth = 300
  rPxHeight = 300
  svgCenterX = this.rPxWidth / 2
  svgCenterY = this.rPxHeight / 2
  scale = 0
  svgPoints: Array<{ x: number, y: number, color: string}> = [];

  constructor(protected common: CommonInfoService, private pointService: PointService) {
  }


  ngOnChanges(changes: SimpleChanges): void {
    this.updateSvgPoints()
  }

  protected svgError = ""

  updateSvgPoints() {
    if (!this.common.points || this.r === 0) {
      this.svgPoints = [];
      return;
    }
    this.scale = this.rPxWidth / (this.r); //  this.scale = this.rPxWidth / (2 * this.r);
    this.svgPoints = this.common.points.map(point => {
      const x = Number(point.x);
      const y = Number(point.y);

      return {
        x: this.svgCenterX + x * this.scale,
        y: this.svgCenterY - y * this.scale,
        color: point.status === 'Попадание' ? 'red' : 'green',
        // originalPoint: point
      };
    });

  }

  refreshPoints() {
    this.updateSvgPoints();
  }

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
        'visibility:': 'visible'
      }
    } else {
      return {
        'visibility:': 'hidden'
      }
    }
  }
}
