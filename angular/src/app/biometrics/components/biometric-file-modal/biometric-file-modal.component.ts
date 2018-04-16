import { Component, OnInit, TemplateRef } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

import { Subject } from 'rxjs/Subject';


@Component({
  selector: 'app-biometric-file-modal',
  templateUrl: './biometric-file-modal.component.html',
  styleUrls: ['./biometric-file-modal.component.css']
})
export class BiometricFileModalComponent implements OnInit {
  public onClose: Subject<boolean>;

  constructor(private _bsModalRef: BsModalRef) {

  }

  ngOnInit() {
  }

  public onConfirm(): void {
    this.onClose.next(true);
    this._bsModalRef.hide();
  }

  public onCancel(): void {
    this.onClose.next(false);
    this._bsModalRef.hide();
  }
}
