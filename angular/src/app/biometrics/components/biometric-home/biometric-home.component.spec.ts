import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BiometricHomeComponent } from './biometric-home.component';
import {
  RouterTestingModule
} from '@angular/router/testing';

describe('BiometricFormComponent', () => {
  let component: BiometricHomeComponent;
  let fixture: ComponentFixture<BiometricHomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [BiometricHomeComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
