import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Point} from '../models/Point';
import {CommonInfoService} from './common-info-service';
import {PointRequest} from '../dto/PointRequest';
import {Observable, tap} from 'rxjs';
import {PointResponse} from '../dto/PointResponse';
import {AuthResponse} from '../dto/AuthResponse';
import {AuthRequest} from '../dto/AuthRequest';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  constructor(private http: HttpClient, private common: CommonInfoService) {
  }

  sendPoint(request: PointRequest): Observable<PointResponse> {
    const url = `http://localhost:25230/lab4/app/points/${this.common.userId}/add-point`
    console.log(`(data-service)requestPoint(${request.x}, ${request.y}, ${request.r})`)
    return this.http.post<PointResponse>(url, request)
  }


  getPoints(): Observable<PointResponse> {
    const url = `http://localhost:25230/lab4/app/points/${this.common.userId}/get-points`;
    console.log(this.common.userId)
    return this.http.get<PointResponse>(url).pipe(
      tap((response: PointResponse) => {
        const points: Point[] = response.points || [];
        this.common.setPoints(points);
      })
    );
  }

// todo https || hash
  getLogInResponse(request: AuthRequest): Observable<AuthResponse> {
    const url = `http://localhost:25230/lab4/app/auth/log-in`
    return this.http.post<AuthResponse>(url, request)
  }

  getRegisterResponse(request: AuthRequest): Observable<AuthResponse> {
    const url = `http://localhost:25230/lab4/app/auth/register`
    return this.http.post<AuthResponse>(url, request)
  }
}
