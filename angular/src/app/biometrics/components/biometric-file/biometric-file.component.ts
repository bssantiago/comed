import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { IFile } from '../../../shared/interfaces/Ifile';
import { HttpClient } from '@angular/common/http';
import { IGenericResponse } from '../../../shared/interfaces/user-response';
import { map, find, isNil } from 'lodash';
import { environment } from '../../../../environments/environment';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { BiometricFileModalComponent } from '../biometric-file-modal/biometric-file-modal.component';
import { BiometricService } from '../../services/biometric.service';
import { IKeyValues } from '../../../shared/interfaces/user-info';
import { IDynamicTable, IListTableItems } from '../../../shared/interfaces/ITable';
import { IClient } from '../../../shared/interfaces/IClientInfo';

@Component({
  selector: 'app-biometric-file',
  templateUrl: './biometric-file.component.html',
  styleUrls: ['./biometric-file.component.less']
})
export class BiometricFileComponent implements OnInit {
  public modalRef: BsModalRef;
  public isModalShown = false;
  public rewardDateError = false;
  public rangeError = false;
  public fileError = false;
  public disabled = true;
  public file: IFile = {
    clientId: '',
    programId: '',
    range: undefined,
    rewardDate: undefined,
    data: null
  };

  public filename = 'Select file...';

  public table: IDynamicTable = {
    currentPage: 0,
    data: [],
    header: [
      { key: 'client_id', value: 'Client Id' },
      { key: 'program_display_name', value: 'Program' },
      { key: 'file_name', value: 'File Name' }],
    pages: 0,
    pageSize: 10,
    filter: {}
  };

  public clients: Array<IClient> = [];

  @ViewChild('fileInput') fileInput: ElementRef;
  // public selectedDateRange: any;
  constructor(private httpClient: HttpClient, private modalService: BsModalService, private bservice: BiometricService) { }

  ngOnInit() {
    this.getFiles();
    this.getClients();
  }

  public notifyChangePage(page: number): void {
    this.table.currentPage = page;
    this.refreshGrid();

  }

  public pageSizeChange(size: number): void {
    this.table.pageSize = size;
    this.refreshGrid();
  }

  public onFileChange(event: any) {

    if (event.target.files && event.target.files[0]) {
      this.fileError = false;
      const reader = new FileReader();
      const file = event.target.files[0];
      reader.onload = (e) => {
        this.file.data = reader.result;
        this.filename = file.name;
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  public rangeChange(): void {
    this.rangeError = false;
  }

  public rewardChange(): void {
    this.rewardDateError = false;
  }

  public upload(model: IFile, isValid: boolean) {
    if (isValid) {
      const request = this.clientAssessmentMapper(model)[0];
      this.bservice.uploadFile2(request).subscribe(pepe => {
        this.refreshGrid();
      });
    } else {
      this.fileError = isNil(model.data);
      this.rewardDateError = isNil(model.rewardDate);
      this.rangeError = isNil(model.range);
    }

  }

  public openModal() {
    this.isModalShown = true;
    this.modalRef = this.modalService.show(BiometricFileModalComponent);
    /*this.modalRef.content.onClose.subscribe(result => {
      console.log('results', result);
    });*/
  }

  public clientChange(clientId: string) {
    const client: IClient = find(this.clients, (item: IClient) => {
      return item.id.toString() === clientId;
    });
    if (isNil(client.program)) {
      this.disabled = false;
    } else {
      this.file.programId = client.program + ' -' + new Date(client.reward_date).toLocaleDateString();
    }

  }

  private clientAssessmentMapper(model: IFile): any {
    return map([model], (data: IFile) => {
      return {
        client_id: data.clientId,
        program_id: data.programId,
        file: this.file.data,
        program_start_date: data.range.start,
        program_end_date: data.range.end,
        program_display_name: data.clientId + data.programId,
        calendar_year: 2018,
        extended_screenings: 0,
        created_by: null,
        creation_date: null,
        last_update_by: null,
        last_update_date: null,
        file_name: 'test',
        reward_date: model.rewardDate
      };
    });
  }

  private refreshGrid(): void {
    this.getFiles();
  }

  private getFiles(): void {
    this.bservice.getClientAssessments({
      page: this.table.currentPage,
      pageSize: this.table.pageSize
    }).subscribe((data: IListTableItems) => {
      this.table.data = data.items;
      this.table.pages = data.pages;
    });
  }

  private getClients(): void {
    this.bservice.getClients().subscribe((data: Array<IClient>) => {
      this.clients = data;
    });
  }


}
