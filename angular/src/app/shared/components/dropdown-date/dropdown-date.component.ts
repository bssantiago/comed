import { Component, OnInit, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { isNavigationCancelingError } from '@angular/router/src/shared';
import { isNil } from 'lodash';

export interface IMonths {
  value: number;
  name: string;
}


@Component({
  selector: 'app-dropdown-date',
  templateUrl: './dropdown-date.component.html',
  styleUrls: ['./dropdown-date.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => DropdownDateComponent),
      multi: true
    }
  ]
})
export class DropdownDateComponent implements ControlValueAccessor {

  private date: Date;
  private months: Array<IMonths> = [
    { name: 'Jan', value: 0 }, { name: 'Feb', value: 1 }, { name: 'Mar', value: 2 }, { name: 'Apr', value: 3 },
    { name: 'May', value: 4 }, { name: 'Jun', value: 5 }, { name: 'Jul', value: 6 }, { name: 'Aug', value: 7 },
    { name: 'Sep', value: 8 }, { name: 'Oct', value: 9 }, { name: 'Nov', value: 10 }, { name: 'Dec', value: 11 }];

  public propagateChange = (_: any) => { };

  public writeValue(obj: any): void {
    this.date = obj;
  }
  public registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }
  public registerOnTouched(fn: any): void {
    throw new Error('Method not implemented.');
  }
  public setDisabledState?(isDisabled: boolean): void {
    throw new Error('Method not implemented.');
  }

  public removeDate(): void {
    this.date = null;
    this.propagateChange(this.date);
  }

  get daysInMonth(): Array<number> {
    const auxDate = new Date();
    const m = (isNil(this.month)) ? auxDate.getMonth() : this.month;
    const y = (isNil(this.year)) ? auxDate.getFullYear() : this.year;
    const amountOfDays = new Date(y, m, 0).getDate();
    return Array(amountOfDays).fill(0).map((x, i) => (i + 1));
  }

  get years(): Array<number> {
    const auxDate = new Date();
    const pastYears = 150;
    const baseYear = auxDate.getFullYear() - pastYears;
    return Array(pastYears).fill(0).map((x, i) => (i + 1) + baseYear);
  }

  constructor() { }

  set day(value) {
    this.checkDate();
    this.date.setDate(value);
    this.propagateChange(this.date);
  }

  get day() {
    return isNil(this.date) ? null : this.date.getDate();
  }

  set month(value) {
    this.checkDate();
    this.date.setMonth(value);
    this.propagateChange(this.date);
  }

  get month() {
    return isNil(this.date) ? null : this.date.getMonth();
  }

  set year(value) {
    this.checkDate();
    this.date.setFullYear(value);
    this.propagateChange(this.date);
  }

  get year() {
    return isNil(this.date) ? null : this.date.getFullYear();
  }

  private checkDate(): void {
    if (isNil(this.date)) {
      this.date = new Date();
      this.date.setUTCHours(0, 0, 0, 0);
    }
  }

}
