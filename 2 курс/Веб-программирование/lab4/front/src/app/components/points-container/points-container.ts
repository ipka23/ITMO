import { Component } from '@angular/core';
import {PointsTableComponent} from '../points-table/points-table';
import {SvgGraphComponent} from '../svg-graph/svg-graph';

@Component({
  selector: 'app-points-container',
  imports: [PointsTableComponent, SvgGraphComponent],
  templateUrl: './points-container.html',
  styleUrl: './points-container.css',

  standalone: true
})
export class PointsContainer {

}
