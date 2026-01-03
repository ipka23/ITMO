import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonInfoService} from '../../services/common-info-service';
import {DataService} from '../../services/data-service';
import {AuthRequest} from '../../dto/AuthRequest';
import {Router} from '@angular/router';
import {AuthResponse} from '../../dto/AuthResponse';
import {configDefaults} from 'vitest/config';

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
  errorMessage = ''

  redirectToRegister() {
    this.router.navigate(['/register'])
  }

  submitForm() {
    console.log(`${this.username}\n${this.password}`)
    this.dataService.getLogInResponse(new AuthRequest(this.username, this.password)).subscribe({
      next: (response) => {
        // if (response.isValid) {
          this.common.userId = parseInt(response.id)
          this.router.navigate(['/points/' + response.id.toString()])
        // }
      },
      error: (err)=> {
        const errorResponse: AuthResponse = err.error;
        this.errorMessage = errorResponse.message
        console.error('Ошибка входа:', errorResponse.message);
        // console.error(errorResponse)
      }
    })
  }
}
