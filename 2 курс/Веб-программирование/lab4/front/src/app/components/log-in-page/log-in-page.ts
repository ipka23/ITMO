import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonInfoService} from '../../services/common-info-service';
import {DataService} from '../../services/data-service';
import {AuthRequest} from '../../dto/AuthRequest';
import {Router} from '@angular/router';

@Component({
  selector: 'app-auth-page',
  imports: [
    FormsModule
  ],
  templateUrl: './log-in-page.html',
  styleUrl: './log-in-page.css',
  standalone: true
})
export class LogInPage {
// после логина common.userId = userId
  constructor(private common: CommonInfoService, private dataService: DataService, private router: Router) {
  }

  username = ''
  password = ''
  redirectToRegister() {
    this.router.navigate(['/register'])
  }

  submitForm() {
    this.dataService.getLogInResponse(new AuthRequest(this.username, this.password))
  }
}
