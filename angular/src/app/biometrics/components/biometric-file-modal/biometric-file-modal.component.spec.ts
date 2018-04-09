import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BiometricFileModalComponent } from './biometric-file-modal.component';

describe('BiometricFileModalComponent', () => {
  let component: BiometricFileModalComponent;
  let fixture: ComponentFixture<BiometricFileModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BiometricFileModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricFileModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
