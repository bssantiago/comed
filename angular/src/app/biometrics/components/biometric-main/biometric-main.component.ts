import { Component, OnInit } from '@angular/core';
import { IUserInfo, IKeyValues } from '../../../shared/interfaces/user-info';
import { BiometricService } from '../../services/biometric.service';
import { IGenericResponse } from '../../../shared/interfaces/user-response';
import { map, isNil, each } from 'lodash';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../../shared/services/toast.service';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-biometric-main',
  templateUrl: './biometric-main.component.html',
  styleUrls: ['./biometric-main.component.less']
})
export class BiometricMainComponent implements OnInit {


  public user: IUserInfo;
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
      this.loadUserData();
    });
    setInterval(() => { this.seconds++; }, 1000);
  }

  public docLetter(): void {
    if (this.existBiometrics) {
      const width = window.innerWidth;
      const newWin: any = window.open(`/comed/#/biometrics/healthletter/${this.participantId}`,
        'Doctor Letter', `width=${width},height=768`);
    } else {
      this.toast.error('This patient does not have any biometric information.', 'Error');
    }
  }

  public healthLetter() {
    if (this.existBiometrics) {
      const width = window.innerWidth;
      const newWin: any = window.open(`/comed/#/reports/health/${this.participantId}`,
        'Doctor Letter', `width=${width},height=768`);
    } else {
      this.toast.error('This patient does not have any biometric information.', 'Error');
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

  public save(model: any, isValid: boolean, f: any): void {

    if (isValid) {
      model.participant_id = this.participantId;
      model.duration = this.seconds;
      const aux = model.heightfeet + '.' + model.heightinches;
      delete model['heightfeet'];
      delete model['heightinches'];
      model.height = parseFloat(aux);
      if (this.isNewBiometrics) {
        this.bservice.saveBiometric(model)
          .subscribe((data: IGenericResponse<any>) => {
            this.toast.success('New biometric information has been created.', 'Success');
            this.loadUserData();
            f.submitted = false;
            this.switchEntries();
          });
      } else {
        model.biometric_id = this.user.biometric_id;
        this.bservice.update(model)
          .subscribe((data: IGenericResponse<any>) => {
            this.toast.success('Biometric information has been updated.', 'Success');
            this.loadUserData();
            f.submitted = false;
            this.switchEntries();
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
      .subscribe((data: any) => {
        this.existBiometrics = !isNil(data.biometric_id);
        data.date_of_birth = new Date(data.date_of_birth);
        data.reward_date = new Date(data.reward_date);
        const aux = data.height.toString().split('.');
        data.height = {
          feet: aux[0],
          inches: aux[1]
        };
        // this.user = data;
        this.setUserCommonData(data);
        this.lastEntryUser = data;
        this.lastEntryUser.assessment_date = new Date();
      });
  }

  private loadUserData() {
    this.user = {
      body_fat: undefined,
      cholesterol: undefined,
      diastolic: undefined,
      duration: undefined,
      fasting: false,
      glucose: undefined,
      hba1c: undefined,
      hdl: undefined,
      ldl: undefined,
      sistolic: undefined,
      tobacco_use: false,
      triglycerides: undefined,
      waist: undefined,
      weight: undefined,
      height: {
        feet: undefined,
        inches: undefined
      },
    };
    if (!isNil(this.participantId)) {
      this.getUser();
      this.user.draw_type = 'In-Person';
    } else {
      this.isNewBiometrics = true;

    }
  }

}
