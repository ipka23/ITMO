import {Point} from '../utility/Point';
import {Injectable} from '@angular/core';
import {LoggerService} from './logger.service';
import {httpResource} from '@angular/common/http';

@Injectable()
export class PointsService {
  private points: Point[] | undefined;

  constructor(private loggerService: LoggerService) {
  }

  getPoints(): Point[] {
    // this.points = httpResource.getPoints


    // this.loggerService.log(points)
    return []
  }

  addPoint(point: Point): void {

  }
}
