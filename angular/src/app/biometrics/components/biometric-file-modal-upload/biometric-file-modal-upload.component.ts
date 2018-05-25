import { Component, OnInit, TemplateRef, Output, EventEmitter } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

import { Subject } from 'rxjs/Subject';
import { BiometricService } from '../../services/biometric.service';
import { IClient } from '../../../shared/interfaces/IClientInfo';
import { IFileModal } from '../../../shared/interfaces/Ifile';
import { find, isNil } from 'lodash';
import { environment } from '../../../../environments/environment';
import { ToastService } from '../../../shared/services/toast.service';
import { LocalStorageService } from '../../../shared/services/local-storage.service';


@Component({
  selector: 'app-biometric-file-modal-upload',
  templateUrl: './biometric-file-modal-upload.component.html',
  styleUrls: ['./biometric-file-modal-upload.component.css']
})
export class BiometricFileModalUploadComponent implements OnInit {
  public onClose: Subject<boolean>;

  constructor(private _bsModalRef: BsModalRef,
    private bservice: BiometricService,
    private toast: ToastService,
    private localStorageService: LocalStorageService) {

  }

  ngOnInit() {
    this.onClose = new Subject();
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
