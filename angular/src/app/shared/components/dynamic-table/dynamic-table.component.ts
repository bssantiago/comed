import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { IKeyValues } from '../../interfaces/user-info';

@Component({
  selector: 'app-dynamic-table',
  templateUrl: './dynamic-table.component.html',
  styleUrls: ['./dynamic-table.component.css']
})
export class DynamicTableComponent implements OnInit {

  @Output() notifyChangePage: EventEmitter<number> = new EventEmitter<number>();
  @Input() public data: Array<any>;
  @Input() public headers: Array<IKeyValues>;
  @Input() public page = 1;
  @Input() public pageSize: number;
  @Input() public pages: number;

  public currentPage;

  constructor() { }

  ngOnInit() {

  }

  changePage(page) {
    this.page = page;
    this.notifyChangePage.emit(page);
  }

}
