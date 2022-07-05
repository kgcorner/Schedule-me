import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecordProcessorMonthlyJobsComponent } from './record-processor-monthly-jobs.component';

describe('RecordProcessorMonthlyJobsComponent', () => {
  let component: RecordProcessorMonthlyJobsComponent;
  let fixture: ComponentFixture<RecordProcessorMonthlyJobsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecordProcessorMonthlyJobsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecordProcessorMonthlyJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
