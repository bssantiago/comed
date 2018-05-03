import { Component, OnInit } from '@angular/core';
import { IUserInfo, IKeyValues } from '../../../shared/interfaces/user-info';
import { BiometricService } from '../../services/biometric.service';
import { IGenericResponse } from '../../../shared/interfaces/user-response';
import { map, isNil } from 'lodash';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../../shared/services/toast.service';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-biometric-main',
  templateUrl: './biometric-main.component.html',
  styleUrls: ['./biometric-main.component.less']
})
export class BiometricMainComponent implements OnInit {

  public user: IUserInfo = {
    body_fat: 0,
    cholesterol: 0,
    diastolic: 0,
    duration: 0,
    fasting: false,
    glucose: 0,
    hba1c: 0,
    hdl: 0,
    ldl: 0,
    sistolic: 0,
    tobacco_use: false,
    triglycerides: 0,
    waist: 0,
    weight: 0,
    height: 0
  };
  public url: string;
  public existBiometrics = false;
  public drawTypes: Array<IKeyValues> = [{
    key: 'In-Person',
    value: 'In-Person'
  },
  {
    key: 'Physician Derived Results',
    value: 'Physician Derived Results'
  },
  {
    key: 'Lab Voucher',
    value: 'Lab Voucher'
  }];
  public clients: Array<IKeyValues> = [];
  public programs: Array<IKeyValues> = [];
  public participantId: number;
  public seconds = 0;
  public isNewBiometrics = true;

  public lastEntryUser: IUserInfo;
  public newEntryUser: IUserInfo;

  constructor(private route: ActivatedRoute, private bservice: BiometricService,
    private toast: ToastService, private router: Router) { }

  ngOnInit() {
    this.url = environment.apiUrl;
    this.route.params.subscribe(params => {
      this.participantId = +params['id'];
      if (!isNil(this.participantId)) {
        this.getUser();
        this.user.draw_type = 'In-Person';
      } else {
        this.isNewBiometrics = true;

      }
    });
    setInterval(() => { this.seconds++; }, 1000);
  }

  public docLetter(): void {
    if (this.existBiometrics) {
      window.open(this.url + 'participant/pdf?participant_id=' + this.participantId);
    } else {
      this.toast.error('This client does not have biometric info', 'Error');
    }
  }

  public switchEntries() {
    if (this.isNewBiometrics) {
      this.newEntryUser = this.user;
    } else {
      this.lastEntryUser = this.user;
    }
    this.isNewBiometrics = !this.isNewBiometrics;
    this.user = (this.isNewBiometrics) ? this.newEntryUser : this.lastEntryUser;
  }

  public save(model: IUserInfo, isValid: boolean): void {
    console.log(model, isValid);
    if (isValid) {
      model.participant_id = this.participantId;
      model.duration = this.seconds;
      if (this.isNewBiometrics) {
        this.bservice.saveBiometric(model)
          .subscribe((data: IGenericResponse<any>) => {
            this.toast.success('New biometric created', 'Success');
            this.router.navigate([`/biometrics/search/`]);
          });
      } else {
        model.biometric_id = this.user.biometric_id;
        this.bservice.update(model)
          .subscribe((data: IGenericResponse<any>) => {
            this.toast.success('Biometric modificated', 'Success');
            this.router.navigate([`/biometrics/search/`]);
          });
      }
    }
  }

  private setUserCommonData(user: IUserInfo) {
    this.user.first_name = user.first_name;
    this.user.last_name = user.last_name;
    this.user.date_of_birth = user.date_of_birth;
    this.user.member_id = user.member_id;
    this.user.program_display_name = user.program_display_name;
    this.user.assessment_date = new Date();
    this.user.reward_date = user.reward_date;
    this.user.participant_id = this.participantId;
  }

  private getUser(): void {
    this.bservice.getUserInfo(this.participantId)
      .subscribe((data: IUserInfo) => {
        this.existBiometrics = !isNil(data.biometric_id);
        data.date_of_birth = new Date(data.date_of_birth);
        data.reward_date = new Date(data.reward_date);
        // this.user = data;
        this.setUserCommonData(data);
        this.lastEntryUser = data;
        this.lastEntryUser.assessment_date = new Date();
      });
  }

}
