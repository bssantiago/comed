import { Component, OnInit, forwardRef, Input } from '@angular/core';
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

  @Input() fieldsDisabled: boolean;
  private date: Date;
  private dateJSON = {
    day: null,
    month: null,
    year: null
  };

  public months: Array<IMonths> = [
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
    // throw new Error('Method not implemented.');
  }
  public setDisabledState?(isDisabled: boolean): void {
    // throw new Error('Method not implemented.');
  }

  public removeDate(): void {
    this.date = null;
    this.dateJSON = {
      day: null,
      month: null,
      year: null
    };
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
    const pastYears = auxDate.getFullYear() - 1899;
    const baseYear = auxDate.getFullYear();
    const arrayYears = [];
    for (let i = 0; i < pastYears; i++) {
      arrayYears[i] = baseYear - i;
    }
    return arrayYears;
    // return Array(pastYears).fill(0).map((x, i) =>  + baseYear);
  }

  constructor() { }

  set day(value) {
    this.dateJSON.day = value;
    this.generateDate();
  }

  get day() {
    return this.dateJSON.day;
  }

  set month(value) {
    this.dateJSON.month = value;
    this.generateDate();
  }

  get month() {
    return this.dateJSON.month;
  }

  set year(value) {
    this.dateJSON.year = value;
    this.generateDate();
  }

  get year() {
    return this.dateJSON.year;
  }

  private checkDate(): void {
    if (isNil(this.date)) {
      this.date = new Date();
      this.date.setUTCHours(0, 0, 0, 0);
    }
  }

  private generateDate() {
    if (this.dateJSON.day && this.dateJSON.month && this.dateJSON.year) {
      this.checkDate();
      this.date.setFullYear(this.dateJSON.year);
      this.date.setDate(this.dateJSON.day);
      this.date.setMonth(this.dateJSON.month);
      this.propagateChange(this.date);
    }
  }

}
