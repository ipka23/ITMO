import {Component} from '@angular/core';
import {CoordinatesFormComponent} from '../coordinates-form/coordinates-form';
import {SvgGraphComponent} from '../svg-graph/svg-graph';
import {PointsTableComponent} from '../points-table/points-table';
import {BehaviorSubject} from 'rxjs';


@Component({
  selector: 'app-main-page',
  imports: [CoordinatesFormComponent, PointsTableComponent, SvgGraphComponent],
  templateUrl: './main-page.html',
  styleUrl: './main-page.css',
  standalone: true
})
export class MainPage {

}
