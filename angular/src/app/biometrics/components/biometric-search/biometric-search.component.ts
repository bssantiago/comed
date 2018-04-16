import { Component, OnInit, ViewChild } from '@angular/core';
import { IKeyValues } from '../../../shared/interfaces/user-info';
import { IParticipantSearch, IParticipantResult } from '../../../shared/interfaces/participant-info';
import { BiometricService } from '../../services/biometric.service';
import { map } from 'lodash';
import { CompleterService, CompleterData } from 'ng2-completer';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/from';
import 'rxjs/add/operator/delay';
import { Router } from '@angular/router';


@Component({
  selector: 'app-biometric-search',
  templateUrl: './biometric-search.component.html',
  styleUrls: ['./biometric-search.component.less']
})
export class BiometricSearchComponent implements OnInit {
  public pages: number;
  public firstNames: any;
  public lastNames: Array<string> = [];
  public user: IParticipantSearch = {
    lastname: '',
    name: ''
  };
  public drawTypes: Array<IKeyValues> = [];
  public clients: Array<IKeyValues> = [];
  public programs: Array<IKeyValues> = [];
  public actionItem: IKeyValues = { key: 'participant_id', value: 'Action' };
  protected dataService: CompleterData;

  public header: Array<IKeyValues> = [
    { key: 'member_id', value: 'Member Id' },
    { key: 'first_name', value: 'Name' },
    { key: 'last_name', value: 'Last name' },
    { key: 'address', value: 'Address' },
    { key: 'participant_id', value: 'Action' }];
  public tableData: Array<any> = [];

  constructor(private bservice: BiometricService, private completerService: CompleterService, private route: Router) {

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
      this.bservice.search(this.user).subscribe((data: IParticipantResult) => {
        this.tableData = data.items;
        this.pages = data.pages;
      });
    }
  }

  public notifyChangePage(page) {
    this.user.page = page;
    this.bservice.search(this.user).subscribe((data: IParticipantResult) => {
      this.tableData = data.items;
      this.pages = data.pages;
    });
  }

  public actionPerformed(id: number): void {
    this.route.navigate([`/biometrics/user/${id}`]);
  }

  public Selected(item: any) {
    console.log(item);
  }

}
