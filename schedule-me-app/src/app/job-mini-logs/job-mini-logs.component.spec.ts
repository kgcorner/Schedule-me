import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobMiniLogsComponent } from './job-mini-logs.component';

describe('JobMiniLogsComponent', () => {
  let component: JobMiniLogsComponent;
  let fixture: ComponentFixture<JobMiniLogsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JobMiniLogsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobMiniLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
