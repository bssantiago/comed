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
    this.bservice.getClients().subscribe((data: Array<IKeyValues>) => {
      this.clients = data;
    });
    this.bservice.getDrawTypes().subscribe((data: Array<IKeyValues>) => {
      this.drawTypes = data;
    });
    this.bservice.getPrograms().subscribe((data: Array<IKeyValues>) => {
      this.programs = data;
    });
    this.getDummyUser();
  }

  public save(model: IUserInfo, isValid: boolean): void {
    console.log(model, isValid);
    if (isValid) {

    }
  }

  private getDummyUser(): void {
    this.bservice.getUserInfo().subscribe((data: IUserInfo) => {
      this.user = data;
    });
  }

}
