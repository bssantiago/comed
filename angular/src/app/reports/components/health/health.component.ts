import { Component, OnInit, AfterViewChecked } from '@angular/core';
import * as html2canvas from 'html2canvas';
import * as jsPDF from 'jspdf';
import { ReportService } from '../../services/report.service';
import { ActivatedRoute } from '@angular/router';
import { IBioGuidline } from '../../../shared/interfaces/Ireport';
import SharedConstats from '../../../shared/constants';
import { isNaN, isNil } from 'lodash';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/forkJoin';

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
        setTimeout(() => {
          this.test();
        }, 500);
      });
    }
  }

  public test() {
    const elem = document.getElementById('health');
    const elem1 = document.getElementById('first');
    const elem2 = document.getElementById('second');
    const elem3 = document.getElementById('thierd');

    Observable.forkJoin([
      html2canvas(elem, { allowTaint: true }),
      html2canvas(elem1, { allowTaint: true }),
      html2canvas(elem2, { allowTaint: true }),
      html2canvas(elem3, { allowTaint: true })
    ])
      .subscribe(t => {
        const canvas = t[0];
        const canvas1 = t[1];
        const canvas2 = t[2];
        const canvas3 = t[3];
        const image = canvas.toDataURL('image/png');
        const image1 = canvas1.toDataURL('image/png');
        const image2 = canvas2.toDataURL('image/png');
        const image3 = canvas3.toDataURL('image/png');
        const doc = new jsPDF('p', 'mm');
        const width = doc.internal.pageSize.width;
        const height = doc.internal.pageSize.height;

        const ratio = elem.clientWidth / doc.internal.pageSize.width;
        const h = elem.clientHeight / ratio;

        doc.addImage(image, 'PNG', 0, 0, width, h);
        doc.addPage();
        doc.addImage(image1, 'PNG', 0, 0, width, h);
        doc.addPage();
        doc.addImage(image2, 'PNG', 0, 0, width, h);
        doc.addPage();
        doc.addImage(image3, 'PNG', 0, 0, width, h);
        doc.autoPrint();
        // doc.save('health.pdf');
        // doc.output('save', 'filename.pdf');
        window.open(doc.output('bloburl'), '_self');
      }, (err) => {
        console.log('error canvas', err);
      });
  }

}
