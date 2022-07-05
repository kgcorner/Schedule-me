import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecordProcessorJobsComponent } from './record-processor-jobs.component';

describe('RecordProcessorJobsComponent', () => {
  let component: RecordProcessorJobsComponent;
  let fixture: ComponentFixture<RecordProcessorJobsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecordProcessorJobsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecordProcessorJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
