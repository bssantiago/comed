import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { IFile } from '../../../shared/interfaces/Ifile';

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
  constructor() { }

  ngOnInit() {
  }

  public onFileChange(event: any) {
    const reader = new FileReader();
    if (event.target.files && event.target.files.length > 0) {
      const file = event.target.files[0];
      this.file.data = file;
    }
  }

  public upload(model: IFile, isValid: boolean) {
    // console.log(this.selectedDateRange);
    model.data = this.file.data;
    if (model.data && isValid) {
      console.log(model, isValid);
    }
  }

}
