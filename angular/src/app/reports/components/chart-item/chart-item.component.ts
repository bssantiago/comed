import { Component, OnInit, Input, AfterViewInit } from '@angular/core';
declare let d3: any;

@Component({
  selector: 'app-chart-item',
  templateUrl: './chart-item.component.html',
  styleUrls: ['./chart-item.component.css']
})
export class ChartItemComponent implements OnInit, AfterViewInit {
  // public data: any;
  // public options: any;
  @Input() public css: string;
  @Input() public options;
  @Input() public data;

  constructor() { }
  ngAfterViewInit() {

  }
  ngOnInit() {


  }

}
