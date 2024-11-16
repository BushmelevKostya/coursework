import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import {EventFormComponent} from "./pages/event-form/event-form.component";
import {UserProfileComponent} from "./features/user-profile/user-profile.component";

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'create-event', component: EventFormComponent },
    { path: 'profile', component: UserProfileComponent},
    { path: '**', redirectTo: '' }
];
