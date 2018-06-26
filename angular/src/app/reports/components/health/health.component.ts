import { Component, OnInit, AfterViewChecked, ViewChild, ElementRef } from '@angular/core';
import * as html2canvas from 'html2canvas';
import * as jsPDF from 'jspdf';
import { ReportService } from '../../services/report.service';
import { ActivatedRoute } from '@angular/router';
import { IBioGuidline } from '../../../shared/interfaces/Ireport';
import SharedConstats from '../../../shared/constants';
import { isNaN, isNil } from 'lodash';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/forkJoin';
import { Http } from '@angular/http';

@Component({
  selector: 'app-health',
  templateUrl: './health.component.html',
  styleUrls: ['./health.component.css']
})
export class HealthComponent implements OnInit {

  @ViewChild('chart1') input: ElementRef;
  public value = 5.3;
  public pid: number = null;
  public bData: any = null;
  public canvas: any;
  private ctx: any;
  private img: any;
  public guidlines: IBioGuidline = SharedConstats.guidlines;
  public nvd3Styles: any;

  constructor(private route: ActivatedRoute, private reportService: ReportService, private http: Http) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.pid = +params['pid'];
    });
    if (!isNaN(this.pid)) {
      this.reportService.health(this.pid).subscribe((data: any) => {
        this.bData = data[0];
        setTimeout(() => {
          this.test();
        }, 2000);
      });
    }
  }

  public state(state: String, data: string) {
    switch (data) {
      case 'sistolic': this.bData.sistolicState = state;
        break;
      case 'diastolic': this.bData.diastolicState = state;
        break;
      case 'cholesterol': this.bData.cholesterolState = state;
        break;
      case 'hdl': this.bData.hdlState = state;
        break;
      case 'ldl': this.bData.ldlState = state;
        break;
      case 'triglycerides': this.bData.triglyceridesState = state;
        break;
      case 'fasting': this.bData.fastingState = state;
        break;
      case 'hba1c': this.bData.hba1cState = state;
        break;
      case 'bmi': this.bData.bmiState = state;
        break;
      case 'body_fat': this.bData.body_fatState = state;
        break;
      case 'waist': this.bData.waistState = state;
        break;
    }
  }

  private canvasToImage(backgroundColor: any): any {
    this.canvas = document.getElementById('canvas');
    const w = this.canvas.width;
    const h = this.canvas.height;
    this.ctx = this.canvas.getContext('2d');
    let data, compositeOperation;
    if (backgroundColor) {
      data = this.ctx.getImageData(0, 0, w, h);
      compositeOperation = this.ctx.globalCompositeOperation;
      this.ctx.globalCompositeOperation = 'destination-over';
      this.ctx.fillStyle = backgroundColor;
      this.ctx.fillRect(0, 0, w, h);
    }

    const imageData = this.canvas.toDataURL('image/octet-stream');

    if (backgroundColor) {
      this.ctx.clearRect(0, 0, w, h);
      this.ctx.putImageData(data, 0, 0);
      this.ctx.globalCompositeOperation = compositeOperation;
    }
    return imageData;
  }

  public createChartImage(leftMargin: boolean, rightMargin: boolean, index: number): any {
    this.img = new Image();
    const rawSVG = document.getElementsByTagName('svg')[index];
    const $temp = document.createElement('div');

    if (leftMargin) {
      rawSVG.children[0].setAttribute('transform', 'translate(115, 60)');
    } else if (rightMargin) {
      rawSVG.children[0].setAttribute('transform', 'translate(20, 60)');
    } else {
      // rawSVG.children[0].setAttribute('transform', 'translate(100, 60)');
    }
    // const title = document.createElement('foreignObject');
    // title.setAttribute('width', '100%');
    // title.setAttribute('height', '100%');
    // title.innerHTML = `<div xmlns='http://www.w3.org/1999/xhtml' style='text-align:center;margin-top:10px;
    // font-family:Open Sans,sans-serif;'></div>
    // <div xmlns='http://www.w3.org/1999/xhtml' style='text-align:center;font-family:Open Sans,sans-serif;'>
    //  </div>`;
    // rawSVG.insertBefore(title, rawSVG.firstChild);
    const height = document.getElementsByTagName('svg')[0].height.baseVal.value + 50;

    rawSVG.setAttribute('height', height.toString());
    if (!window.navigator.msSaveOrOpenBlob) {
      rawSVG.setAttribute('xmlns', 'http://www.w3.org/2000/svg');
    }
    const styles = document.createElement('style');
    styles.setAttribute('type', 'text/css');
    styles.innerHTML = SharedConstats.nvd3Style;

    const defs = document.createElement('defs');
    defs.appendChild(styles);
    rawSVG.insertBefore(defs, rawSVG.firstChild);
    const $node = rawSVG.cloneNode(true);
    $temp.appendChild($node);
    const svgData = $temp.innerHTML;
    this.img.src = 'data:image/svg+xml;base64,' + window.btoa(svgData);
    return this.img;
  }

  public test() {
    const g1 = document.getElementById('g1');
    const g2 = document.getElementById('g2');
    const g3 = document.getElementById('g3');
    const g4 = document.getElementById('g4');
    const g5 = document.getElementById('g5');
    const g6 = document.getElementById('g6');

    const imgg = this.createChartImage(false, false, 0);
    g1.appendChild(imgg);

    const imgg1 = this.createChartImage(false, false, 1);
    g2.appendChild(imgg1);

    const imgg2 = this.createChartImage(false, false, 2);
    g3.appendChild(imgg2);

    const imgg3 = this.createChartImage(false, false, 3);
    g4.appendChild(imgg3);

    const imgg4 = this.createChartImage(false, false, 4);
    g5.appendChild(imgg4);

    const imgg5 = this.createChartImage(false, false, 5);
    g6.appendChild(imgg5);

    const elem = document.getElementById('health');
    const elem1 = document.getElementById('graphs1');
    const elem2 = document.getElementById('graphs2');
    const elem3 = document.getElementById('graphs3');

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
        const width = doc.internal.pageSize.width - 20;
        const height = doc.internal.pageSize.height;

        const ratio = elem.clientWidth / elem.clientHeight;
        const h = width / ratio;

        const ratio1 = elem1.clientWidth / elem1.clientHeight;
        const ratio2 = elem2.clientWidth / elem2.clientHeight;
        const ratio3 = elem3.clientWidth / elem3.clientHeight;

        const h1 = width / ratio1;
        const h2 = width / ratio2;
        const h3 = width / ratio3;

        doc.addImage(image, 'PNG', 10, 20, width, h);
        doc.addPage();
        doc.addImage(image1, 'PNG', 10, 20, width, h1);
        doc.addImage(image2, 'PNG', 10, 150, width, h2);
        doc.addPage();
        doc.addImage(image3, 'PNG', 10, 20, width, h3);
        doc.autoPrint();
        console.log('descargando');
        if (window.navigator.msSaveOrOpenBlob) {
          doc.output('save', 'health.pdf');
          window.navigator.msSaveOrOpenBlob(doc.output('bloburl'), 'health.pdf');
        } else {
          window.open(doc.output('bloburl'), '_self');
        }

      }, (err) => {
        console.log('error canvas', err);
      });
  }

}
