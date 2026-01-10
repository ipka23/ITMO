import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {PointService} from '../../services/point-service';
import {CommonInfoService} from '../../services/common-info-service';
import {FormsModule} from '@angular/forms';
import {NgStyle} from '@angular/common';
import {CommonModule} from '@angular/common';
import {Point} from '../../models/Point';

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
export class SvgGraphComponent implements OnInit {
  rPxWidth = 300
  rPxHeight = 300
  svgCenterX = 150
  svgCenterY = 150
  rPxSize = this.rPxWidth / 3
  scale = 0
  svgPoints: Array<{ x: number, y: number, color: string }> = [];

  constructor(protected common: CommonInfoService, private pointService: PointService, private cdr: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.common.points$.subscribe(points => {
      if (points != null) {
        this.getSvgPoints(points)
      }

    })

    this.common.r$.subscribe(r => {
      if (r != null && r > 0) {
        this.updateSvgPoints(r)
      }
    })

  }


  protected svgError = ""

  updateSvgPoints(r: number) {
    this.scale = this.rPxSize / r
    console.log(`r: ${r} scale: ${this.scale}`)
    this.getSvgPoints(this.common.points ?? [])

  }

  getSvgPoints(points: Point[]) {
    this.svgPoints = points.map(point => {
      const x = Number(point.x);
      const y = Number(point.y);
      const currentR = this.common.r;

      const svgX = this.svgCenterX + x * this.scale
      const svgY = this.svgCenterY - y * this.scale

      const isHit = this.checkPointHit(x, y, currentR)
      const color = isHit ? 'green' : 'red'

      return {
        x: svgX,
        y: svgY,
        color: color
      }
    })
    this.cdr.detectChanges()
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


  private checkPointHit(x: number, y: number, r: number): boolean {
    if (r <= 0) {
      return false;
    }
    return this.checkCircle(x, y, r) || this.checkTriangle(x, y, r) || this.checkRectangle(x, y, r);
  }

  private checkCircle(x: number, y: number, r: number): boolean {
    if (x <= 0 && y <= 0) {
      return x ** 2 + y ** 2 <= r ** 2;
    }
    return false;
  }

  private checkTriangle(x: number, y: number, r: number): boolean {
    if (x <= 0 && y >= 0) {
      return y < 2*x + r;
    }
    return false;
  }


  private checkRectangle(x: number, y: number, r: number): boolean {
    if (x >= 0 && y <= 0) {
      return x < r / 2 && y > -r;
    }
    return false;
  }
}
