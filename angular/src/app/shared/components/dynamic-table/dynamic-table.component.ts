import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-dynamic-table',
  templateUrl: './dynamic-table.component.html',
  styleUrls: ['./dynamic-table.component.css']
})
export class DynamicTableComponent implements OnInit {

  @Input() public data: Array<any>;
  @Input() public headers: Array<string>;
  @Input() public page: number;
  @Input() public pageSize: number;
  constructor() { }

  ngOnInit() {
  }

}
