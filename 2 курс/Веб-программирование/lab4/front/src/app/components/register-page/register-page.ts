import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthRequest} from '../../dto/AuthRequest';
import {CommonInfoService} from '../../services/common-info-service';
import {DataService} from '../../services/data-service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register-page',
  imports: [
    FormsModule
  ],
  templateUrl: './register-page.html',
  styleUrl: './register-page.css',
})
export class RegisterPage {
  constructor(private common: CommonInfoService, private dataService: DataService, private router: Router) {
  }

  username = ''
  password1 = ''
  password2 = ''
  errorMessage = ''

  redirectToLogIn() {
    this.router.navigate(['/log-in'])
  }

  submitForm() {
    console.log(`${this.username}\n${this.password1}\n${this.password2}`)
    if (this.password1 !== this.password2) {
      this.errorMessage = 'Пароли не совпадают!'
      setTimeout(() => {
        this.errorMessage = ''
      }, 3000)
      this.password1 = ''
      this.password2 = ''
      return
    } else {
      this.dataService.getRegisterResponse(new AuthRequest(this.username, this.password1)).subscribe({
        next: (response) => {
          if (response.isValid) {
            this.common.userId = +response.id
            this.router.navigate(['/points/' + response.id])
          }
        },
        error: (response) => {
          this.errorMessage = response.message
        }
      })
    }

  }

}
