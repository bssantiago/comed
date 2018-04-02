import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BiometricFileComponent } from './biometric-file.component';

describe('BiometricFileComponent', () => {
  let component: BiometricFileComponent;
  let fixture: ComponentFixture<BiometricFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BiometricFileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
