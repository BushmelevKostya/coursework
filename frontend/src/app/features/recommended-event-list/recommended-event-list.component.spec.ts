import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecommendedEventListComponent } from './recommended-event-list.component';

describe('RecommendedEventListComponent', () => {
  let component: RecommendedEventListComponent;
  let fixture: ComponentFixture<RecommendedEventListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecommendedEventListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecommendedEventListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
