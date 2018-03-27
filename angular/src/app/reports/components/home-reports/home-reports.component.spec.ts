import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeReportsComponent } from './home-reports.component';

describe('HomeReportsComponent', () => {
  let component: HomeReportsComponent;
  let fixture: ComponentFixture<HomeReportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeReportsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeReportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
