import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobAuditsComponent } from './job-audits.component';

describe('JobAuditsComponent', () => {
  let component: JobAuditsComponent;
  let fixture: ComponentFixture<JobAuditsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JobAuditsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobAuditsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
