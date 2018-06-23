import { Component, OnInit, Input } from '@angular/core';
import { ReportService } from '../../services/report.service';
import { ISeries } from '../../../shared/interfaces/Ireport';
import { map, isNil } from 'lodash';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  @Input() public pid: number;
  @Input() public reportType: string;
  @Input() public unit: string;
  public title: string;
  public data: Array<ISeries> = [];
  public init = new Date().getTime();
  public end = new Date().getTime();

  constructor(private reportService: ReportService) { }

  ngOnInit() {
    this.reportService.history(this.pid, this.reportType).subscribe((data: any) => {

      if (data.length > 0 && this.reportType === 'blood') {
        const info = map(data, (item: any) => {
          return { x: new Date(item.x).getTime(), y: item.y };
        });
        const info1 = map(data, (item: any) => {
          return { x: new Date(item.x).getTime(), y: item.y1 };
        });
        this.data = [
          {
            values: info,
            key: 'sistolic',
            color: '#ff7f0e'
          },
          {
            values: info1,
            key: 'diastolic',
            color: '#2ca02c'
          }
        ];
      } else {
        this.data = [
          {
            values: map(data, (item: any) => {
              return { x: new Date(item.x).getTime(), y: item.y };
            }),
            key: this.reportType,
            color: '#ff7f0e',
          }
        ];

      }
      this.init = new Date().getTime();
      this.end = undefined;
      if (this.data) {
        this.data.forEach(d => {
          d.values.forEach((v: any) => {
            if (v.x <= this.init) {
              const date = new Date(v.x);
              date.setMonth(date.getMonth() - 1);
              this.init = date.getTime();
              // console.log(new Date(v.x));
            }

            if (! this.end ) {
              const date = new Date(v.x);
              date.setMonth(date.getMonth() + 1);
              this.end = date.getTime();
            } else if (this.end <= v.x) {
              const date = new Date(v.x);
              date.setMonth(date.getMonth() + 1);
              this.end = date.getTime();
            }
          });
          // console.log(d);
        });
      }
      // console.log(new Date(this.init));
      // console.log(new Date(this.end));
    });


  }


  get chartOptions(): any {
    return {
      chart: {
        xScale: d3.time.scale(),
        xDomain: [new Date(this.init), new Date(this.end)],
        type: 'lineChart',
        height: 450,
        width: 450,
        margin: {
          top: 20,
          right: 20,
          bottom: 40,
          left: 55
        },
        x: function (d) { return d.x; },
        y: function (d) { return d.y; },
        useInteractiveGuideline: false,
        dispatch: {
          stateChange: function (e) { console.log('stateChange'); },
          changeState: function (e) { console.log('changeState'); },
          tooltipShow: function (e) { console.log('tooltipShow'); },
          tooltipHide: function (e) { console.log('tooltipHide'); }
        },
        xAxis: {
          axisLabel: 'Dates',
          tickFormat: function (d) {
            return d3.time.format('%m/%Y')(new Date(d));
          },
          /*tickFormat: function (d) {
            return d3.time.format('%Y-%m-%d')(new Date(d));
          },*/
          showMaxMin: true
        },
        yAxis: {
          axisLabel: this.unit,
          tickFormat: function (d) {
            return d3.format('.02f')(d);
          },
          axisLabelDistance: -10
        },
        showXAxis: 'true',
        callback: function (chart) {

        }
      }
    };
  }

}
