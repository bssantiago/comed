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


  public user: IUserSearch;
  public drawTypes: Array<IKeyValues> = [];
  public clients: Array<IKeyValues> = [];
  public programs: Array<IKeyValues> = [];

  public header: Array<string> = ['memberId', 'name', 'lastname', 'address'];
  public tableData: Array<any> = [];

  constructor(private bservice: BiometricService) { }

  ngOnInit() {
    this.user = {};
    this.bservice.getClients().subscribe((data: Array<IKeyValues>) => {
      this.clients = data;
    });
  }

  public search(model: IUserSearch, isValid: boolean): void {
    console.log('search', model);
    if (isValid) {
      this.bservice.search(model).subscribe((data: Array<IUserInfo>) => {
        this.tableData = map(data, (item: IUserInfo) => {
          return item;
        });
      });
    }
  }

}
