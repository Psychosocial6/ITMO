import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteDialog } from './delete-dialog';

describe('DeleteDialog', () => {
  let component: DeleteDialog;
  let fixture: ComponentFixture<DeleteDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeleteDialog]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteDialog);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
