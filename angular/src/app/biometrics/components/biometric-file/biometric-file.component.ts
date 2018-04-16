import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { IFile } from '../../../shared/interfaces/Ifile';
import { HttpClient } from '@angular/common/http';
import { IGenericResponse } from '../../../shared/interfaces/user-response';
import { map } from 'lodash';
import { environment } from '../../../../environments/environment.prod';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { BiometricFileModalComponent } from '../biometric-file-modal/biometric-file-modal.component';
import { BiometricService } from '../../services/biometric.service';
import { IKeyValues } from '../../../shared/interfaces/user-info';

@Component({
  selector: 'app-biometric-file',
  templateUrl: './biometric-file.component.html',
  styleUrls: ['./biometric-file.component.less']
})
export class BiometricFileComponent implements OnInit {
  public modalRef: BsModalRef;
  public isModalShown = false;
  public file: IFile = {
    clientId: '',
    programId: '',
    range: undefined,
    rewardDate: undefined,
    data: null
  };

  public filename = 'Select file...';
  public header: Array<IKeyValues> = [
    { key: 'client_id', value: 'Client Id' },
    { key: 'program_display_name', value: 'Program' },
    { key: 'file_name', value: 'File Name' }];
  public tableData: Array<any> = [];

  public table: any = {
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

  @ViewChild('fileInput') fileInput: ElementRef;
  // public selectedDateRange: any;
  constructor(private httpClient: HttpClient, private modalService: BsModalService, private bservice: BiometricService) { }

  ngOnInit() {
    this.bservice.getClientAssessments({
      page: 0,
      pageSize: 10
    }).subscribe((data: any) => {
      this.table.data = data.items;
      this.table.pages = data.pages;
    });
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
      const reader = new FileReader();
      const file = event.target.files[0];
      reader.onload = (e) => {
        this.file.data = reader.result;
        this.filename = file.name;
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  public upload(model: IFile, isValid: boolean) {
    // console.log(this.selectedDateRange);
    // model.data = this.file.data;

    if (isValid) {
      const request = this.clientAssessmentMapper(model)[0];
      this.httpClient
        .post(`${environment.apiUrl}client_assessment`, request, { withCredentials: true })
        .map((res: any) => {
          return res.result;
        }).subscribe(pepe => {
          this.refreshGrid();
        });
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

    this.bservice.getClientAssessments({
      page: this.table.currentPage,
      pageSize: this.table.pageSize
    }).subscribe((data: IGenericResponse<any>) => {
      this.table.data = data.response.items;
      this.table.pages = data.response.pages;
    });
  }

  openModal() {
    this.isModalShown = true;
    this.modalRef = this.modalService.show(BiometricFileModalComponent);
    this.modalRef.content.onClose.subscribe(result => {
        console.log('results', result);
    });
  }

}
