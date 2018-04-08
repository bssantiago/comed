import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { IFile } from '../../../shared/interfaces/Ifile';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-biometric-file',
  templateUrl: './biometric-file.component.html',
  styleUrls: ['./biometric-file.component.less']
})
export class BiometricFileComponent implements OnInit {

  public file: IFile = {
    clientId: '',
    programId: '',
    range: undefined,
    rewardDate: undefined,
    data: null
  };

  @ViewChild('fileInput') fileInput: ElementRef;
  // public selectedDateRange: any;
  constructor(private httpClient: HttpClient) { }

  ngOnInit() {
  }

  public onFileChange(event: any) {

    if (event.target.files && event.target.files[0]) {
      const reader = new FileReader();
      const file = event.target.files[0];
      reader.onload = (e) => {
        this.file.data = reader.result;
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  public upload(model: IFile, isValid: boolean) {
    // console.log(this.selectedDateRange);
    // model.data = this.file.data;

    const request: any = {};
    request.client_id = this.file.clientId;
    request.program_id = this.file.programId;
    request.calendar_year = 2018;
    request.program_start_date = null;
    request.program_end_date = null;
    request.program_display_name = this.file.clientId + this.file.programId;
    request.extended_screenings = 0;
    request.created_by = null;
    request.creation_date = null;
    request.last_update_by = null;
    request.last_update_date = null;
    request.file = this.file.data;
    // if (model.data && isValid) {
    //   console.log(model, isValid);
      this.httpClient
        .post(`http://localhost:8081/comed/rest/private/client_assessment`, request, { withCredentials: true })
        .map((res: any) => {
          return res.result;
        }).subscribe(pepe => {
          console.log();
        });
    // }
  }

}
