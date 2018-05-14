import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { IFile, IFileTable } from '../../../shared/interfaces/Ifile';
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
import { ToastService } from '../../../shared/services/toast.service';
import { LocalStorageService } from '../../../shared/services/local-storage.service';

@Component({
  selector: 'app-biometric-file',
  templateUrl: './biometric-file.component.html',
  styleUrls: ['./biometric-file.component.less']
})
export class BiometricFileComponent implements OnInit {
  public modalRef: BsModalRef;
  public optionsErrors = this.getOptionsAndErrors();
  public file: IFile = this.getInitialFormData();
  public table: IDynamicTable = this.getTableOptions();
  public clientId = null;
  public clients: Array<IClient> = [];
  @ViewChild('fileInput') fileInput: ElementRef;
  public localStorageClientId: string;

  constructor(private httpClient: HttpClient,
    private modalService: BsModalService,
    private bservice: BiometricService,
    private toast: ToastService,
    private localStorageService: LocalStorageService) { }

  ngOnInit() {
    this.localStorageClientId = this.localStorageService.getClientId();
    this.clientId = this.localStorageService.getClientId();
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
    this.optionsErrors.fileError = false;
    if (event.target.files && event.target.files[0]) {
      const file = event.target.files[0];
      if (!isNil(file) && file.type.indexOf('vnd.ms-excel') > -1) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.file.data = reader.result;
          this.optionsErrors.filename = file.name;
        };
        reader.readAsDataURL(event.target.files[0]);
      } else {
        this.optionsErrors.fileError = true;
      }
    }
  }

  public rangeChange(): void {
    this.optionsErrors.rangeError = false;
  }

  public rewardChange(): void {
    this.optionsErrors.rewardDateError = false;
  }

  public clientChange(clientId: string) {
    const client: IClient = find(this.clients, (item: IClient) => {
      return item.id.toString() === clientId;
    });
    if (!isNil(this.localStorageClientId)) {
      this.file.clientId = clientId;
      this.optionsErrors.disabled = true;
    } else {
      this.optionsErrors.disabled = false;
    }
    if (isNil(client) || isNil(client.program)) {
      this.file.programId = '';
    } else {
      this.file.programId = client.program;
    }

  }

  public upload(model: IFile, isValid: boolean) {
    if (isValid) {
      this.toast.success('You are uploading a new eligibility file and will remove all previous eligibility files for this client', '');
      model.clientId = this.file.clientId;
      const request = this.clientAssessmentMapper(model)[0];
      this.bservice.upload(request).subscribe(res => {
        if (res) {
          this.refreshGrid();
        }
      }, err => this.toast.error(err, 'Error'));
    } else {
      this.optionsErrors.fileError = isNil(model.data);
      this.optionsErrors.rewardDateError = isNil(model.rewardDate);
      this.optionsErrors.rangeError = isNil(model.range);
    }
  }

  public openModal() {
    this.optionsErrors.isModalShown = true;
    this.modalRef = this.modalService.show(BiometricFileModalComponent);
  }

  private clientAssessmentMapper(model: IFile): any {
    return map([model], (data: IFile) => {
      return {
        client_id: data.clientId,
        program_id: data.programId,
        file: this.file.data,
        program_start_date: data.range.start,
        program_end_date: data.range.end,
        program_display_name: data.programId,
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
    }).subscribe((data: IListTableItems<IFileTable>) => {
      this.table.data = data.items;
      this.table.pages = data.pages;
    });
  }

  private getClients(): void {
    this.bservice.getClients().subscribe((data: Array<IClient>) => {
      this.clients = data;
      this.clientChange(this.localStorageClientId);
    });
  }

  private getOptionsAndErrors(): any {
    return {
      isModalShown: false,
      rewardDateError: false,
      rangeError: false,
      fileError: false,
      disabled: false,
      filename: 'Select file...'
    };
  }

  private getInitialFormData(): IFile {
    return {
      clientId: '',
      programId: '',
      range: undefined,
      rewardDate: undefined,
      data: null
    };
  }

  private getTableOptions(): IDynamicTable {
    return {
      currentPage: 1,
      data: [],
      header: [
        { key: 'client_name', value: 'Client Name' },
        { key: 'program_display_name', value: 'Program' },
        { key: 'file_name', value: 'File Name' }],
      pages: 0,
      pageSize: 10,
      filter: {}
    };
  }

}
