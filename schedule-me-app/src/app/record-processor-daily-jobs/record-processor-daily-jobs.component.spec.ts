import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecordProcessorDailyJobsComponent } from './record-processor-daily-jobs.component';

describe('RecordProcessorDailyJobsComponent', () => {
  let component: RecordProcessorDailyJobsComponent;
  let fixture: ComponentFixture<RecordProcessorDailyJobsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecordProcessorDailyJobsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecordProcessorDailyJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
