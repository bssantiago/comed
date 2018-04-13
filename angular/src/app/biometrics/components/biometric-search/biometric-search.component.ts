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
  styleUrls: ['./biometric-search.component.css']
})
export class BiometricSearchComponent implements OnInit {

  public firstNames: any;
  public lastNames: Array<string> = [];
  public user: IUserSearch;
  public drawTypes: Array<IKeyValues> = [];
  public clients: Array<IKeyValues> = [];
  public programs: Array<IKeyValues> = [];
  protected dataService: CompleterData;

  public header: Array<string> = ['memberId', 'name', 'lastname', 'address'];
  public tableData: Array<any> = [];

  private searchData = [
    { color: 'red', value: '#f00' },
    { color: 'green', value: '#0f0' },
    { color: 'blue', value: '#00f' },
    { color: 'cyan', value: '#0ff' },
    { color: 'magenta', value: '#f0f' },
    { color: 'yellow', value: '#ff0' },
    { color: 'black', value: '#000' }
  ];

  constructor(private bservice: BiometricService, private completerService: CompleterService) {

    this.dataService = completerService.local(this.getFirstNames(), 'color', 'color');

  }

  ngOnInit() {
    this.user = {};
    this.bservice.getClients().subscribe((data: Array<IKeyValues>) => {
      this.clients = data;
    });
  }

  public search(model: IUserSearch, isValid: boolean): void {
    if (isValid) {
      this.bservice.search(model).subscribe((data: Array<IUserInfo>) => {
        this.tableData = map(data, (item: IUserInfo) => {
          return item;
        });
      });
    }
  }

  public Selected(item: any) {
    console.log(item);
  }

  public getFirstNames() {
    return Observable.from([this.searchData]).delay(3000);
    /*
    if (this.user.name.length > 2) {
      this.bservice.getFirstNames(this.user.name).subscribe((data: Array<string>) => {
        const info = map(data, (item: string) => {
          return {
            title: item, id: item,
          };
        });
      });
    }
*/
  }

  public getLastNames() {
    if (this.user.lastname.length > 2) {
      this.bservice.getLastNames(this.user.lastname).subscribe((data: Array<string>) => {
        this.lastNames = map(data, (item: string) => {
          return {
            searchText: item,
            name: item,
            id: item
          };
        });
      });
    }
  }

}
