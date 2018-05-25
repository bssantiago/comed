import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { IGuidline, IBioGuidline, IRangeGuid } from '../../../shared/interfaces/Ireport';
import { each } from 'lodash';

@Component({
  selector: 'app-report-bar',
  templateUrl: './report-bar.component.html',
  styleUrls: ['./report-bar.component.css']
})
export class ReportBarComponent implements OnInit {

  @Input() public value: number;
  @Input() public guidline: Array<IRangeGuid>;
  @Output() state = new EventEmitter<String>();

  public color: string;
  public width = 0;
  public values: Array<string> = [];

  constructor() { }

  ngOnInit() {
    this.width = this.calculatePercentage(this.value);
    this.color = this.getBarColor(this.guidline, this.value);
    this.generateValues(this.value);
  }

  private getBarColor(guid: Array<IRangeGuid>, num: number): string {
    let color = '';
    each(guid, (g: IRangeGuid) => {
      if (this.isBetween(g.min, g.max, num)) {
        color = g.color;
        this.state.emit(g.color);
      }
    });
    return color;
  }

  private isBetween(min: number, max: number, num: number): boolean {
    if (num >= min && num <= max) {
      return true;
    } else {
      return false;
    }
  }

  private calculatePercentage(num: number): number {
    const nearest = this.calculateNearPoint(num);
    const percentage = num * 50 / nearest;
    return percentage;
  }

  private calculateNearPoint(num: number): number {
    return Math.round(num);
  }

  private generateValues(num: number): void {
    const nearest = this.calculateNearPoint(num);
    this.values = [nearest.toString()];
    for (let i = 1; i < 5; i++) {
      const val = (i * 2);
      const positive = nearest + val;
      const negative = nearest - val;

      this.values.push('\'');
      this.values.push(Math.round(positive).toString());
      this.values.unshift('\'');
      this.values.unshift(Math.round(negative).toString());

    }

  }

}
