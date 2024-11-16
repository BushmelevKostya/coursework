import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserProfileComponent} from './user-profile/user-profile.component';
import {EventListComponent} from './event-list/event-list.component';

@NgModule({
  declarations: [UserProfileComponent, EventListComponent],
  imports: [CommonModule],
  exports: [UserProfileComponent, EventListComponent]
})
export class FeaturesModule { }
