import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BiometricSearchComponent } from './biometric-search.component';

describe('BiometricSearchComponent', () => {
  let component: BiometricSearchComponent;
  let fixture: ComponentFixture<BiometricSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BiometricSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
