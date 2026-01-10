import {Point} from '../models/Point';
import {Injectable, OnInit} from '@angular/core';
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

  constructor(private common: CommonInfoService, private dataService: DataService) {
  }


  drawPointByClick($e: PointerEvent): void {
    const svg = $e.currentTarget as SVGSVGElement
    const r: number = this.common.r

    if (r === -1 || r === 0 || r.toString() === "") {
      this.svgError = "Выберите R!"
      return
    }

    const absoluteX = $e.clientX
    const absoluteY = $e.clientY
    const absolutePoint = svg.createSVGPoint()
    absolutePoint.x = absoluteX
    absolutePoint.y = absoluteY

    const screenCTM = svg.getScreenCTM()
    if (!screenCTM) {
      console.error('Cannot get SVG CTM')
      return
    }

    const svgPoint = absolutePoint.matrixTransform(screenCTM.inverse())
    const svgX = svgPoint.x
    const svgY = svgPoint.y

    const coords = this.svgToMathCoords(svgX, svgY, r)

    const request: PointRequest = {x: coords.x.toString(), y: coords.y.toString(), r: r.toString()}
    console.log(`PointService: requestPoint_newPoint(${request.x}, ${request.y}, ${request.r})`)

    this.dataService.sendPoint(request).subscribe({
      next: (response) => {
        const newPoint: Point = {x: response.x, y: response.y, r: response.r, status: response.status, currentTime: response.currentTime, executionTime: response.executionTime}
        console.log(`PointService: responsePoint(${newPoint.x}, ${newPoint.y}, ${newPoint.r}, ${newPoint.status}, ${newPoint.currentTime}, ${newPoint.executionTime})`)
        this.common.addPoint(newPoint);
      },
      error: (err) => {
        console.log(err.error)
      }
    })
  }


  svgToMathCoords(svgX: number, svgY: number, r: number) {
    const rPxSize = 300 / 3
    const svgCenterX = 150
    const svgCenterY = 150

    const scale = rPxSize / r

    return {
      x: ((svgX - svgCenterX) / scale).toFixed(2),
      y: ((svgCenterY - svgY) / scale).toFixed(2)
    }
  }


}
