import {bootstrapApplication} from '@angular/platform-browser';
import {provideRouter} from '@angular/router';
import {provideHttpClient, HTTP_INTERCEPTORS} from '@angular/common/http';
import {routes} from './app/app-routing/app-routing-module';
import {App} from './app/components/app/app';
import {AuthInterceptor} from './app/interceptors/auth.interceptor';

bootstrapApplication(App, {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
    }
  ]
}).catch((err) => console.error(err));
