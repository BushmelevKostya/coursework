import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import {EventFormComponent} from "./pages/event-form/event-form.component";
import {UserProfileComponent} from "./pages/user-profile/user-profile.component";
import {GamesComponent} from "./pages/games/games.component";
import {IconFormComponent} from './pages/icon-form/icon-form.component';
import {UserEventsComponent} from './pages/user-events/user-events.component';

export const routes: Routes = [
    { path: '', component: HomeComponent},
    { path: 'create-event', component: EventFormComponent },
    { path: 'profile', component: UserProfileComponent},
    { path: 'games', component: GamesComponent},
    { path: 'icon', component: IconFormComponent},
    { path: 'user-events', component: UserEventsComponent},
    { path: '**', redirectTo: '' }
];
