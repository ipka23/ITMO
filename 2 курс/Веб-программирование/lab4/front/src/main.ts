import {bootstrapApplication} from '@angular/platform-browser';
import {provideRouter} from '@angular/router';
import {routes} from './app/app-routing/app-routing-module';
import {App} from './app/components/app/app';
bootstrapApplication(App, {
  providers: [
    provideRouter(routes)
  ]
}).catch((err) => console.error(err));
