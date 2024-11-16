import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { HomeComponent } from './app/pages/home/home.component';
import {AppComponent} from './app/app/app.component';

bootstrapApplication(AppComponent, appConfig)
  .catch(err => console.error(err));
