import { Component, OnInit } from '@angular/core';
import { IUserInfo, IKeyValues } from '../../../shared/interfaces/user-info';
import { BiometricService } from '../../services/biometric.service';

@Component({
  selector: 'app-biometric-main',
  templateUrl: './biometric-main.component.html',
  styleUrls: ['./biometric-main.component.less']
})
export class BiometricMainComponent implements OnInit {

  public user: IUserInfo;
  public drawTypes: Array<IKeyValues> = [];
  public clients: Array<IKeyValues> = [];
  public programs: Array<IKeyValues> = [];

  constructor(private bservice: BiometricService) { }

  ngOnInit() {
    this.user = this.getDummyUser();
    this.getUser();
    this.bservice.getClients().subscribe((data: Array<IKeyValues>) => {
      this.clients = data;
    });
    this.bservice.getDrawTypes().subscribe((data: Array<IKeyValues>) => {
      this.drawTypes = data;
    });
    this.bservice.getPrograms().subscribe((data: Array<IKeyValues>) => {
      this.programs = data;
    });
  }

  public save(model: IUserInfo, isValid: boolean): void {
    console.log(model, isValid);
    if (isValid) {

    }
  }

  private getUser(): void {
    this.bservice.getUserInfo().subscribe((data: IUserInfo) => {
      this.user = data;
    });
  }

  private getDummyUser(): IUserInfo {
    return {
      basicInfo: {
        name: '',
        lastname: '',
        client: '',
        memberId: '',
        drawType: '',
        program: '',
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

}
