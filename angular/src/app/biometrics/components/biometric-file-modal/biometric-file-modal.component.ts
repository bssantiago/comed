import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

import { Subject } from 'rxjs/Subject';
import { BiometricService } from '../../services/biometric.service';
import { IClient } from '../../../shared/interfaces/IClientInfo';
import { IFileModal } from '../../../shared/interfaces/Ifile';
import { find, isNil } from 'lodash';
import { environment } from '../../../../environments/environment';
import { ToastService } from '../../../shared/services/toast.service';
import { LocalStorageService } from '../../../shared/services/local-storage.service';
import { NgForm } from '@angular/forms';


@Component({
  selector: 'app-biometric-file-modal',
  templateUrl: './biometric-file-modal.component.html',
  styleUrls: ['./biometric-file-modal.component.css']
})
export class BiometricFileModalComponent implements OnInit {
  public onClose: Subject<boolean>;
  public modal: IFileModal = {
    clientId: '',
    ptrogramDisplayName: '',
    programId: '',
    merked: false
  };
  public disabled = true;
  public clients: Array<IClient> = [];
  public url: string;
  public clientIdStorage = '';
  public submit = false;
  constructor(private _bsModalRef: BsModalRef,
    private bservice: BiometricService,
    private toast: ToastService,
    private localStorageService: LocalStorageService) {

  }

  ngOnInit() {
    this.getClients();
    this.url = environment.apiUrl;
  }

  public clientChangeModal(clientId: string) {
    const client: IClient = find(this.clients, (item: IClient) => {
      return item.id.toString() === clientId;
    });
    if (isNil(client.program)) {
      this.disabled = false;
    } else {
      this.modal.ptrogramDisplayName = client.program;
      this.modal.programId = client.program_id;
    }
  }
  public onConfirm(): void {
    this.onClose.next(true);
    this._bsModalRef.hide();
  }

  public onCancel(): void {
    this.onClose.next(false);
    this._bsModalRef.hide();
  }

  public getTxt(): void {
    this.bservice.getText().subscribe((data: any) => {

    });
  }

  public downloadFile(modal: IFileModal, isValid: boolean): void {
    if (isValid) {
      // window.open(this.url + 'participant/file?client_id=' + this.modal.clientId
      //   + '&program_id=' + this.modal.programId + '&mark_download=' + modal.merked);
      this.submit = false;
      const link = document.createElement('a');
      link.setAttribute('type', 'hidden');
      link.href = this.url + 'participant/file?client_id=' + this.modal.clientId
        + '&program_id=' + this.modal.programId + '&mark_download=' + modal.merked;
      document.body.appendChild(link);
      link.click();
    } else {
      this.submit = true;
    }
  }

  private getClientFromList(clientId: string): IClient {
    const client: IClient = find(this.clients, (item: IClient) => {
      return item.id.toString() === clientId;
    });
    return client;
  }

  private getClients(): void {
    this.bservice.getClients().subscribe((data: Array<IClient>) => {
      this.clients = data;
      this.clientIdStorage = this.localStorageService.getClientId();
      this.modal.clientId = isNil(this.clientIdStorage) ? '' : this.clientIdStorage;
      this.clientChangeModal(this.modal.clientId);
    });
  }
}
