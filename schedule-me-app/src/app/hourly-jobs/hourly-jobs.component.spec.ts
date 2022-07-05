import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HourlyJobsComponent } from './hourly-jobs.component';

describe('HourlyJobsComponent', () => {
  let component: HourlyJobsComponent;
  let fixture: ComponentFixture<HourlyJobsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HourlyJobsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HourlyJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
