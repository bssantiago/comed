import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DropdownDateComponent } from './dropdown-date.component';

describe('DropdownDateComponent', () => {
  let component: DropdownDateComponent;
  let fixture: ComponentFixture<DropdownDateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DropdownDateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DropdownDateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
