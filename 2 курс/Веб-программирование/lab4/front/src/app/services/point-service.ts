import {Point} from '../models/Point';
import {Injectable} from '@angular/core';
import {LoggerService} from './logger-service';
import {HttpClient} from '@angular/common/http';
import {CommonInfoService} from './common-info-service';
import {PointRequest} from '../dto/PointRequest';

import {DataService} from './data-service';

@Injectable({
  providedIn: 'root',
})
export class PointService {
  svgError = ""

  constructor(private logger: LoggerService, private http: HttpClient, private common: CommonInfoService, private dataService: DataService) {
  }


  drawPointByClick($e: PointerEvent): void {
    const svg = $e.currentTarget as SVGGraphicsElement
    const r: number = this.common.r
    const absoluteX = $e.clientX
    const absoluteY = $e.clientY
    const absolutePoint = new SVGPoint()

    absolutePoint.x = absoluteX
    absolutePoint.y = absoluteY

    const screenCTM = svg.getScreenCTM();
    if (screenCTM == null) return

    const svgPoint = absolutePoint.matrixTransform(screenCTM.inverse())
    const svgX = svgPoint.x
    const svgY = svgPoint.y

    const coords = this.svgToMathCoords(svgX, svgY, r, svg)

    if (r === -1) {
      this.svgError = "Выберите R!"
    } else {
      this.dataService.sendPoint(new PointRequest(coords.x, coords.y, r.toString()))

    }
  }


  svgToMathCoords(svgX: number, svgY: number, r: number, svg: SVGGraphicsElement) {
    const rPxSize = svg.clientWidth / 3
    const scale = rPxSize / r
    const svgCenterX = 150
    const svgCenterY = 150
    return {
      x: ((svgX - svgCenterX) / scale).toFixed(2),
      y: ((svgCenterY - svgY) / scale).toFixed(2)
    }
  }


}
