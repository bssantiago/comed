import { Component, OnInit } from '@angular/core';
import { IUserInfo, IKeyValues } from '../../../shared/interfaces/user-info';
import { BiometricService } from '../../services/biometric.service';
import { IGenericResponse } from '../../../shared/interfaces/user-response';
import { map, isNil } from 'lodash';
import { ActivatedRoute } from '@angular/router';

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
  public participantId: number;
  public seconds = 0;

  constructor(private route: ActivatedRoute, private bservice: BiometricService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.participantId = +params['id'];
      if (!isNil(this.participantId)) {
        this.getUser();
      }
    });
    /*
        this.bservice.getDrawTypes().subscribe((data: Array<IKeyValues>) => {
          this.drawTypes = data;
        });*/

    setInterval(() => { this.seconds++; }, 1000);

  }

  public save(model: IUserInfo, isValid: boolean): void {
    console.log(model, isValid);
    if (isValid) {
      model.biometric_id = this.participantId;
      model.duration = this.seconds;
      this.bservice.update(model)
        .subscribe((data: IGenericResponse<any>) => {

        });
    }
  }

  private getUser(): void {
    this.bservice.getUserInfo(this.participantId)
      .subscribe((data: IUserInfo) => {
        console.log('user -', data);
        data.date_of_birth = new Date(data.date_of_birth);
        data.reward_date = new Date(data.reward_date);
        this.user = data;
        this.user.assessment_date = new Date();
      });
  }

}
