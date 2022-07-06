import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmJobDeleteComponent } from './confirm-job-delete.component';

describe('ConfirmJobDeleteComponent', () => {
  let component: ConfirmJobDeleteComponent;
  let fixture: ComponentFixture<ConfirmJobDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfirmJobDeleteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmJobDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
