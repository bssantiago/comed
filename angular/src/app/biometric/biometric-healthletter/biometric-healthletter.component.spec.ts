import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BiometricHealthletterComponent } from './biometric-healthletter.component';

describe('BiometricHealthletterComponent', () => {
  let component: BiometricHealthletterComponent;
  let fixture: ComponentFixture<BiometricHealthletterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BiometricHealthletterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricHealthletterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
