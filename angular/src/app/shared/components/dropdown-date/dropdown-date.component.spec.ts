import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DropdownDateComponent } from './dropdown-date.component';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from '../../shared.module';
import { ToastrModule } from 'ngx-toastr';

describe('DropdownDateComponent', () => {
  let component: DropdownDateComponent;
  let fixture: ComponentFixture<DropdownDateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule,
        RouterTestingModule,
        HttpClientModule,
        ToastrModule.forRoot()],
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
