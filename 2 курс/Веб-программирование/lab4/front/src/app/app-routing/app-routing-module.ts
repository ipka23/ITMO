import {Routes} from '@angular/router';
import {LogInPage} from '../components/log-in-page/log-in-page';
import {RegisterPage} from '../components/register-page/register-page';
import {MainPage} from '../components/main-page/main-page';


export const routes: Routes = [
  {path: "log-in", component: LogInPage},
  {path: "register", component: RegisterPage},
    {path: 'points/:userId', component: MainPage},

  {path: '', redirectTo: 'log-in', pathMatch: 'full'},
  {path: "**", redirectTo: 'log-in'}
]

