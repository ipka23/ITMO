import {Point} from '../models/Point';

export type PointResponse = {
  x: string;
  y: string;
  r: string;
  status: string;
  currentTime: string;
  executionTime: string;
  points?: Point[];
}
