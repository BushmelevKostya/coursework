import { Component } from '@angular/core';
import {SharedModule} from '../../shared/shared.module';
import {FeaturesModule} from '../../features/features.module';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    FeaturesModule,
    SharedModule
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
