import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KudagoEventListComponent } from './kudago-event-list.component';

describe('KudagoEventListComponent', () => {
  let component: KudagoEventListComponent;
  let fixture: ComponentFixture<KudagoEventListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KudagoEventListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KudagoEventListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
