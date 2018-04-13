import { Component, OnInit, TemplateRef } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
@Component({
  selector: 'app-biometric-file-modal',
  templateUrl: './biometric-file-modal.component.html',
  styleUrls: ['./biometric-file-modal.component.css']
})
export class BiometricFileModalComponent implements OnInit {
  constructor(private modalService: BsModalService) { }

  ngOnInit() {
  }
}
