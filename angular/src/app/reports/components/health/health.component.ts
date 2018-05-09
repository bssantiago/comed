import { Component, OnInit } from '@angular/core';
import * as html2canvas from 'html2canvas';
import * as jsPDF from 'jspdf';
import { ReportService } from '../../services/report.service';
import { ActivatedRoute } from '@angular/router';
import { IBioGuidline } from '../../../shared/interfaces/Ireport';
import SharedConstats from '../../../shared/constants';
import { isNaN } from 'lodash';

@Component({
  selector: 'app-health',
  templateUrl: './health.component.html',
  styleUrls: ['./health.component.css']
})
export class HealthComponent implements OnInit {

  public value = 5.3;
  public pid: number = null;
  public bData: any = null;

  public guidlines: IBioGuidline = SharedConstats.guidlines;

  constructor(private route: ActivatedRoute, private reportService: ReportService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.pid = +params['pid'];
    });
    if (!isNaN(this.pid)) {
      this.reportService.health(this.pid).subscribe((data: any) => {
        this.bData = data[0];
      });
    }
  }

  public test() {
    const elem = document.getElementById('sarasa');
    html2canvas(elem)
      .then((canvas) => {
        console.log(canvas);
        // document.body.appendChild(canvas);
        // const img = canvas.toDataURL();
        const image = canvas.toDataURL('image/png'); // .replace('image/png', 'image/octet-stream');
        // here is the most important part because if you dont replace you will get a DOM 18 exception.

        const doc = new jsPDF('p', 'mm', 'a4');
        const ratio = elem.clientWidth / doc.internal.pageSize.width;
        const h = elem.clientHeight / ratio;


        const width = doc.internal.pageSize.width;
        doc.addImage(image, 'PNG', 0, 0, width, h);
        doc.save('sample-file.pdf');


        // window.location.href = image;
        // window.open(canvas.toDataURL());
      })
      .catch(err => {
        console.log('error canvas', err);
      });
  }

}
