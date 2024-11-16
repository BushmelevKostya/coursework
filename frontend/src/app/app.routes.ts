import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import {EventFormComponent} from "./pages/event-form/event-form.component";

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'create-event', component: EventFormComponent },
    { path: '**', redirectTo: '' }
];
