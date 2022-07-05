import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MonthlyJobsComponent } from './monthly-jobs.component';

describe('MonthlyJobsComponent', () => {
  let component: MonthlyJobsComponent;
  let fixture: ComponentFixture<MonthlyJobsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MonthlyJobsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MonthlyJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
