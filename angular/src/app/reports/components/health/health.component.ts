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
import { DomSanitizer } from '@angular/platform-browser';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-health',
  templateUrl: './health.component.html',
  styleUrls: ['./health.component.css']
})
export class HealthComponent implements OnInit {

  public id = 0;
  public url: any;


  constructor(private route: ActivatedRoute, private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = +params['pid'];
      this.url = this.sanitizer.bypassSecurityTrustResourceUrl(`${environment.apiUrl}participant/helath_report?participant_id=${this.id}`);
    });
  }

  load() {
    if (this.url) {
      const iframe: any = document.getElementById('iframe');
      iframe.contentWindow.print();
    }
  }

}
