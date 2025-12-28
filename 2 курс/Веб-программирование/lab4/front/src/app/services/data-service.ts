import {Injectable} from '@angular/core';
import {LoggerService} from './logger-service';
import {HttpClient} from '@angular/common/http';
import {Point} from '../models/Point';
import {CommonInfoService} from './common-info-service';
import {PointRequest} from '../dto/PointRequest';
import {Observable} from 'rxjs';
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
    return this.http.post<PointResponse>(url, request)

  }


  getPoints(): Observable<PointResponse> {
    const url = `http://localhost:25230/lab4/app/points/${this.common.userId}/get-points`
    return this.http.get<PointResponse>(url)
  }

// todo https || hash || и то и то
  getLogInResponse(request: AuthRequest): Observable<AuthResponse> {
    const url = `http://localhost:25230/lab4/app/auth/log-in`
    return this.http.post<AuthResponse>(url, request)
  }

  getRegisterResponse(request: AuthRequest): Observable<AuthResponse> {
    const url = `http://localhost:25230/lab4/app/auth/register`
    return this.http.post<AuthResponse>(url, request)
  }
}
