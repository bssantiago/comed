import { Component, OnInit, ViewChild } from '@angular/core';
import { IUserSearch, IKeyValues, IUserInfo } from '../../../shared/interfaces/user-info';
import { BiometricService } from '../../services/biometric.service';
import { map } from 'lodash';
import { CompleterService, CompleterData } from 'ng2-completer';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/from';
import 'rxjs/add/operator/delay';

@Component({
  selector: 'app-biometric-search',
  templateUrl: './biometric-search.component.html',
  styleUrls: ['./biometric-search.component.less']
})
export class BiometricSearchComponent implements OnInit {

  public firstNames: any;
  public lastNames: Array<string> = [];
  public user: IUserSearch = {
    lastname: '',
    name: ''
  };
  public drawTypes: Array<IKeyValues> = [];
  public clients: Array<IKeyValues> = [];
  public programs: Array<IKeyValues> = [];
  protected dataService: CompleterData;

  public header: Array<IKeyValues> = [
    { key: 'member_id', value: 'Member Id' },
    { key: 'first_name', value: 'Name' },
    { key: 'last_name', value: 'Last name' },
    { key: 'address', value: 'Address' }];
  public tableData: Array<any> = [];

  constructor(private bservice: BiometricService, private completerService: CompleterService) {

  }

  ngOnInit() {
    this.user = {
      pageSize: 10,
      page: 1
    };
  }

  public keydown(event: any): void {
    if (event.currentTarget.value.length > 2) {
      this.user.lastname = event.currentTarget.value;
      this.bservice.getLastNames(this.user.lastname).subscribe((data: Array<string>) => {
        this.lastNames = map(data, (item: string) => {
          return {
            searchText: item,
            name: item,
            id: item
          };
        });
        this.dataService = this.completerService.local(this.lastNames, 'searchText', 'searchText');
      });
    }
  }

  public keydownName(event: any): void {
    if (event.currentTarget.value.length > 2) {
      this.user.name = event.currentTarget.value;
      this.bservice.getFirstNames(this.user.name).subscribe((data: Array<string>) => {
        this.firstNames = map(data, (item: string) => {
          return {
            searchText: item,
            name: item,
            id: item
          };
        });
        this.dataService = this.completerService.local(this.firstNames, 'searchText', 'searchText');
      });
    }
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

  public Selected(item: any) {
    console.log(item);
  }

}
