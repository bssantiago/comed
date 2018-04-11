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

    this.bservice.getClients().subscribe((data: Array<IKeyValues>) => {
      this.clients = data;
    });
    this.bservice.getDrawTypes().subscribe((data: Array<IKeyValues>) => {
      this.drawTypes = data;
    });
    this.bservice.getPrograms().subscribe((data: Array<IKeyValues>) => {
      this.programs = data;
    });

    setInterval(() => { this.seconds++; }, 1000);

  }

  public save(model: IUserInfo, isValid: boolean): void {
    console.log(model, isValid);
    if (isValid) {
      model.duration = this.seconds;
      this.bservice.save(model)
        .subscribe((data: IGenericResponse) => {

        });
    }
  }

  private getUser(): void {
    this.bservice.getUserInfo(this.participantId)
      .subscribe((data: IUserInfo) => {
        this.user = data;
      });
  }

}
