import { Component, OnInit, TemplateRef } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

import { Subject } from 'rxjs/Subject';
import { BiometricService } from '../../services/biometric.service';
import { IClient } from '../../../shared/interfaces/IClientInfo';
import { IFileModal } from '../../../shared/interfaces/Ifile';
import { find, isNil } from 'lodash';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-biometric-file-modal',
  templateUrl: './biometric-file-modal.component.html',
  styleUrls: ['./biometric-file-modal.component.css']
})
export class BiometricFileModalComponent implements OnInit {
  public onClose: Subject<boolean>;
  public modal: IFileModal = {
    clientId: '',
    programId: '',
    merked: false
  };
  public disabled = true;
  public clients: Array<IClient> = [];
  public url: string;
  constructor(private _bsModalRef: BsModalRef, private bservice: BiometricService) {

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
      this.modal.programId = client.program.replace(clientId, '');
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
    /*
    this.bservice.getText().subscribe((data: any) => {
      console.log(data);
    });
    */

    this.bservice.getPdf().subscribe((data: any) => {
      console.log(data);
    });
  }

  public downloadFile(modal: IFileModal, isValid: boolean): void {
    
    // marcar como descargado
    // si esta marcado
    // no permite descargar
    // sino
    // permite descargar

    if (isValid) {
      this.bservice.markAsDownloaded({
        program_id: modal.programId,
        client_id: modal.clientId,
        marked: modal.merked
      })
        .subscribe((isMarked: boolean) => {
          if (!isMarked) {
            window.open(this.url + 'participant/file?client_id=' + modal.clientId + '&program_id=' + modal.programId);
          }
        });
    }
  }

  private getClients(): void {
    this.bservice.getClients().subscribe((data: Array<IClient>) => {
      this.clients = data;
    });
  }
}
