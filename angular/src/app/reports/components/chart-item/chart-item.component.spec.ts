import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChartItemComponent } from './chart-item.component';
import { NvD3Module } from 'ng2-nvd3';

describe('TestReportComponent', () => {
  let component: ChartItemComponent;
  let fixture: ComponentFixture<ChartItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [NvD3Module,
      ],

      declarations: [ChartItemComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChartItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
