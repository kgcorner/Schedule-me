import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmJobRunComponent } from './confirm-job-run.component';

describe('ConfirmJobRunComponent', () => {
  let component: ConfirmJobRunComponent;
  let fixture: ComponentFixture<ConfirmJobRunComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfirmJobRunComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmJobRunComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
