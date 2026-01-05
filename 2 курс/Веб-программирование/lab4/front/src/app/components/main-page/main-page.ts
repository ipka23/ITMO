import {Component} from '@angular/core';
import {CoordinatesFormComponent} from '../coordinates-form/coordinates-form';
import {SvgGraphComponent} from '../svg-graph/svg-graph';
import {PointsContainer} from '../points-container/points-container';

@Component({
  selector: 'app-main-page',
  imports: [CoordinatesFormComponent, PointsContainer],
  templateUrl: './main-page.html',
  styleUrl: './main-page.css',
  standalone: true
})
export class MainPage {

}
