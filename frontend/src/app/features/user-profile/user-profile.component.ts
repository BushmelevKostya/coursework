import { Component } from '@angular/core';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  standalone: true,
  imports: [
    NgOptimizedImage
  ],
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent { }
