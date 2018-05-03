import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BiometricHealthLetterComponent } from './biometric-health-letter.component';

describe('BiometricHealthLetterComponent', () => {
  let component: BiometricHealthLetterComponent;
  let fixture: ComponentFixture<BiometricHealthLetterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BiometricHealthLetterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricHealthLetterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
