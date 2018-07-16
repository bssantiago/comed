import { Component, OnInit, OnChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { environment } from '../../../../environments/environment';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-biometric-health-letter',
  templateUrl: './biometric-health-letter.component.html',
  styleUrls: ['./biometric-health-letter.component.css']
})
export class BiometricHealthLetterComponent implements OnInit, OnChanges {

  public id = 0;
  public url: any;

  constructor(private route: ActivatedRoute, private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      this.url = this.sanitizer.bypassSecurityTrustResourceUrl(`${environment.apiUrl}participant/pdf?participant_id=${this.id}`);
    });
  }

  ngOnChanges() {

  }

  load() {
    if (this.url) {
      const iframe: any = document.getElementById('iframe');
      iframe.contentWindow.print();
      iframe.contentWindow.onafterprint = this.afterPrint;
    }
  }

  public afterPrint() {

    window.close();
    console.log('closing doctor letter');
  }

}
