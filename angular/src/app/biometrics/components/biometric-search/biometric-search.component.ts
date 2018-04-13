import { Component, OnInit } from '@angular/core';
import { IUserSearch, IKeyValues, IUserInfo } from '../../../shared/interfaces/user-info';
import { BiometricService } from '../../services/biometric.service';
import { map } from 'lodash';

@Component({
  selector: 'app-biometric-search',
  templateUrl: './biometric-search.component.html',
  styleUrls: ['./biometric-search.component.css']
})
export class BiometricSearchComponent implements OnInit {

  public firstNames: Array<string> = [];
  public lastNames: Array<string> = [];
  public user: IUserSearch;
  public drawTypes: Array<IKeyValues> = [];
  public clients: Array<IKeyValues> = [];
  public programs: Array<IKeyValues> = [];

  public header: Array<IKeyValues> = [
    { key: 'member_id', value: 'Member Id' },
    { key: 'first_name', value: 'Name' },
    { key: 'last_name', value: 'Last name' },
    { key: 'address', value: 'Address' }];

  public tableData: Array<any> = [];

  constructor(private bservice: BiometricService) { }

  ngOnInit() {
    this.user = {
      page: 1,
      pageSize: 10
    };
    this.bservice.getClients().subscribe((data: Array<IKeyValues>) => {
      this.clients = data;
    });
  }

  public search(isValid: boolean): void {
    if (isValid) {
      this.bservice.search(this.user).subscribe((data: Array<IUserInfo>) => {
        this.tableData = map(data, (item: IUserInfo) => {
          return item;
        });
      });
    }
  }

  public getFirstNames() {
    if (this.user.name.length > 2) {
      this.bservice.getFirstNames(this.user.name).subscribe((data: Array<string>) => {
        this.firstNames = data;
      });
    }

  }

  public getLastNames() {
    if (this.user.lastname.length > 2) {
      this.bservice.getLastNames(this.user.lastname).subscribe((data: Array<string>) => {
        this.firstNames = data;
      });
    }
  }

}
