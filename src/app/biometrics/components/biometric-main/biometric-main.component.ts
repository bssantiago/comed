import { Component, OnInit } from '@angular/core';
import { IUserInfo } from '../../../shared/interfaces/user-info';

@Component({
  selector: 'app-biometric-main',
  templateUrl: './biometric-main.component.html',
  styleUrls: ['./biometric-main.component.css']
})
export class BiometricMainComponent implements OnInit {

  public user: IUserInfo;

  constructor() { }

  ngOnInit() {
    this.user = {
      basicInfo: {
        name: '',
        lastname: '',
        client: '',
        memberId: '',
        drawType: '',
        essmentDate: new Date(),
      },
      bodyMeasurements: {
        bodyFat: 0,
        diastolic: 0,
        height: {
          ft: 0,
          in: 0
        },
        sistolic: 0,
        Waist: 0,
        weight: 0
      },
      lipidBloodSugar: {
        cholesterol: 0,
        fasting: false,
        glucose: 0,
        hba1c: 0,
        hdl: 0,
        ldl: 0,
        triglycerides: 0
      },
      tobaccoUse: {
        use: false
      }
    };
  }

  public save(model: IUserInfo, isValid: boolean): void {
    console.log(model, isValid);
    if (isValid) {

    }
  }

}
