import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobLogsComponent } from './job-logs.component';

describe('JobLogsComponent', () => {
  let component: JobLogsComponent;
  let fixture: ComponentFixture<JobLogsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JobLogsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
