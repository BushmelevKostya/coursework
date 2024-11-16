import { Component } from '@angular/core';
import {NgOptimizedImage} from '@angular/common';
import {NavbarComponent} from '../../shared/navbar/navbar.component';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  standalone: true,
  imports: [
    NgOptimizedImage,
    NavbarComponent
  ],
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent { }
