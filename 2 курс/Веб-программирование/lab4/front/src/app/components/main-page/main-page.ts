import {ChangeDetectorRef, Component} from '@angular/core';
import {CoordinatesFormComponent} from '../coordinates-form/coordinates-form';
import {SvgGraphComponent} from '../svg-graph/svg-graph';
import {PointsTableComponent} from '../points-table/points-table';
import {Router} from '@angular/router';
import {CommonInfoService} from '../../services/common-info-service';


@Component({
  selector: 'app-main-page',
  imports: [CoordinatesFormComponent, PointsTableComponent, SvgGraphComponent],
  templateUrl: './main-page.html',
  styleUrl: './main-page.css',
  standalone: true
})
export class MainPage {
  constructor(private router: Router, private common: CommonInfoService, private cdr: ChangeDetectorRef) {
    if (this.common.userId === undefined) {
      this.router.navigate(['log-in']);
    }
    // cdr.detectChanges()
  }
}
