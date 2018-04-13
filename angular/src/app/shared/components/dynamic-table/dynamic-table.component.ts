import { Component, OnInit, Input } from '@angular/core';
import { IKeyValues } from '../../interfaces/user-info';

@Component({
  selector: 'app-dynamic-table',
  templateUrl: './dynamic-table.component.html',
  styleUrls: ['./dynamic-table.component.css']
})
export class DynamicTableComponent implements OnInit {

  @Input() public data: Array<any>;
  @Input() public headers: Array<IKeyValues>;
  @Input() public page: number;
  @Input() public pageSize: number;
  constructor() { }

  ngOnInit() {
  }

}
