import { Component, OnInit, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { IKeyValues } from '../../interfaces/user-info';

@Component({
  selector: 'app-dynamic-table',
  templateUrl: './dynamic-table.component.html',
  styleUrls: ['./dynamic-table.component.css']
})
export class DynamicTableComponent implements OnInit, OnChanges {

  @Output() notifyChangePage: EventEmitter<number> = new EventEmitter<number>();
  @Output() pageSizeChange: EventEmitter<number> = new EventEmitter<number>();
  @Output() actionPerformed: EventEmitter<number> = new EventEmitter<number>();
  @Input() public data: Array<any>;
  @Input() public headers: Array<IKeyValues>;
  @Input() public page = 1;
  @Input() public pageSize: number;
  @Input() public pages: number;

  private PAGINATION_COUNT = 5;
  public pages_list = [];

  constructor() { }

  ngOnInit() {
    this.createPaginationList();
  }

  ngOnChanges() {
    this.createPaginationList();
  }

  public changePage(page): void {
    this.page = page;
    this.notifyChangePage.emit(page);
  }

  public changePageSize(size: number): void {
    this.pageSize = size;
    this.pageSizeChange.emit(size);
  }

  public actionPerform(...args: Array<any>): void {
    this.actionPerformed.emit(args[0]);
  }

  public createPaginationList(): void {
    this.pages_list = [];
    let start = 1;
    if (this.page > Math.floor(this.PAGINATION_COUNT / 2)) {
      start = this.page - Math.floor(this.PAGINATION_COUNT / 2);
    }
    for (let i = start; i < start + this.PAGINATION_COUNT && i <= this.pages; i++) {
      this.pages_list.push(i);
    }
  }
}
