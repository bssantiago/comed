import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BiometricMainComponent } from './biometric-main.component';

describe('BiometricMainComponent', () => {
  let component: BiometricMainComponent;
  let fixture: ComponentFixture<BiometricMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BiometricMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
