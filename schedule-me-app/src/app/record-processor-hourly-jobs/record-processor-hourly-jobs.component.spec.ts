import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecordProcessorHourlyJobsComponent } from './record-processor-hourly-jobs.component';

describe('RecordProcessorHourlyJobsComponent', () => {
  let component: RecordProcessorHourlyJobsComponent;
  let fixture: ComponentFixture<RecordProcessorHourlyJobsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecordProcessorHourlyJobsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecordProcessorHourlyJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
