import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PointForm } from './point-form';

describe('PointForm', () => {
  let component: PointForm;
  let fixture: ComponentFixture<PointForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PointForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PointForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
