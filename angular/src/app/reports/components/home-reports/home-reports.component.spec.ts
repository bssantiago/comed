import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeReportsComponent } from './home-reports.component';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from '../../../shared/shared.module';
import { ToastrModule } from 'ngx-toastr';

describe('HomeReportsComponent', () => {
  let component: HomeReportsComponent;
  let fixture: ComponentFixture<HomeReportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule,
        RouterTestingModule,
        HttpClientModule,
        SharedModule,
        ToastrModule.forRoot()],
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
