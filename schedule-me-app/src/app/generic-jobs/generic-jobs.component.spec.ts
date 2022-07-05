import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericJobsComponent } from './generic-jobs.component';

describe('GenericJobsComponent', () => {
  let component: GenericJobsComponent;
  let fixture: ComponentFixture<GenericJobsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GenericJobsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GenericJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
