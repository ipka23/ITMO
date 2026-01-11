import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { CommonInfoService } from '../services/common-info-service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private common: CommonInfoService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const paramId = route.paramMap.get('userId')
    const currentId = this.common.userId

    if (!currentId || !paramId) {
      this.router.navigate(['points', currentId]);
      return false;
    }

    if (currentId.toString() !== paramId.toString()) {
      this.router.navigate(['points', currentId]);
      return false;
    }

    return true;
  }
}
