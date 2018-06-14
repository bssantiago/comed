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
  public submitted = false;
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
      this.submitted = false;
    } else {
      this.lastEntryUser = this.user;
    }
    this.isNewBiometrics = !this.isNewBiometrics;
    this.user = (this.isNewBiometrics) ? this.newEntryUser : this.lastEntryUser;
  }

  public save(model: any, isValid: boolean, f: any): void {
    this.submitted = true;
    if (isValid && this.validateForm(this.user)) {
      model.participant_id = this.participantId;
      model.duration = this.seconds;
      const aux = model.heightfeet + (model.heightinches / 100);
      delete model['heightfeet'];
      delete model['heightinches'];
      model.height = parseFloat(aux);
      if (this.isNewBiometrics) {
        this.bservice.saveBiometric(model)
          .subscribe((data: IGenericResponse<any>) => {
            this.toast.success('New biometric information has been created.', 'Success');
            this.loadUserData(true);
            this.submitted = false;
          });
      } else {
        model.biometric_id = this.user.biometric_id;
        this.bservice.update(model)
          .subscribe((data: IGenericResponse<any>) => {
            this.toast.success('Biometric information has been updated.', 'Success');
            this.loadUserData(false);
            this.isNewBiometrics = !this.isNewBiometrics;
            this.submitted = false;
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

  private getUser(switchUser: boolean): void {
    this.bservice.getUserInfo(this.participantId)
      .subscribe((data: any) => {
        this.existBiometrics = !isNil(data.biometric_id);
        data.date_of_birth = new Date(data.date_of_birth);
        data.reward_date = new Date(data.reward_date);
        const aux = data.height.toString().includes('.')
          ? data.height.toString().split('.')
          : [data.height, 0];
        data.height = {
          feet: parseInt(aux[0], 10),
          inches: parseFloat('0.' + aux[1]) * 100
        };
        // this.user = data;
        this.setUserCommonData(data);
        this.lastEntryUser = data;
        if (data) {
          this.lastEntryUser.assessment_date = data.create_date ? new Date(data.create_date) : new Date();
        }
        if (switchUser) {
          this.switchEntries();
        }
      });
  }

  private loadUserData(switchUser?: boolean) {
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
      this.getUser(switchUser);
      this.user.draw_type = 'In-Person';
    } else {
      this.isNewBiometrics = true;

    }
  }

  getIsNumber(field: any) {
    return !isNaN(field) &&
      parseInt(field, 10) === field &&
      !isNaN(parseInt(field, 10));
  }

  getIsOnRange(field: number, min: number, max: number) {
    return field >= min && field <= max;
  }

  validateField(field: number, min: number, max: number) {
    return this.getIsNumber(field) && this.getIsOnRange(field, min, max);
  }

  private validateForm(user: IUserInfo): boolean {
    return this.validateField(user.sistolic, 90, 180) &&
      this.validateField(user.diastolic, 60, 110) &&
      this.validateField(user.height.feet, 4, 7) &&
      this.validateField(user.height.inches, 0, 11) &&
      this.validateField(user.weight, 50, 500) &&
      this.getIsNumber(user.waist) &&
      this.getIsOnRange(user.body_fat, 1, 60) &&
      this.validateField(user.cholesterol, 80, 200) &&
      this.validateField(user.hdl, 10, 150) &&
      this.validateField(user.triglycerides, 0, 500) &&
      this.validateField(user.ldl, 10, 250) &&
      this.validateField(user.glucose, 50, 1000) &&
      this.validateField(user.hba1c, 0, 20);
  }

}
