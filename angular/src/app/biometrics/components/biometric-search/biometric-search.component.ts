import { Component, OnInit, ViewChild } from '@angular/core';
import { IKeyValues } from '../../../shared/interfaces/user-info';
import { IParticipantSearch, IParticipantResult } from '../../../shared/interfaces/participant-info';
import { BiometricService } from '../../services/biometric.service';
import { map, isNil, find } from 'lodash';
import { CompleterService, CompleterData } from 'ng2-completer';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/from';
import 'rxjs/add/operator/delay';
import { Router, ActivatedRoute } from '@angular/router';
import { IDynamicTable } from '../../../shared/interfaces/ITable';
import { IClient } from '../../../shared/interfaces/IClientInfo';
import { ToastService } from '../../../shared/services/toast.service';

@Component({
  selector: 'app-biometric-search',
  templateUrl: './biometric-search.component.html',
  styleUrls: ['./biometric-search.component.less']
})
export class BiometricSearchComponent implements OnInit {
  public pages: number;
  public firstNames: any;
  public lastNames: Array<string> = [];
  public programDisabled = false;
  public user: IParticipantSearch = {
    lastname: '',
    name: ''
  };
  public clientItem: any = {
    id: '',
    name: ''
  };
  public props: any = {
    enableOutsideDays: false,
    isDayBlocked: () => false,
  };
  public drawTypes: Array<IKeyValues> = [];
  public clients: Array<IClient> = [];
  public programs: Array<IKeyValues> = [];
  public actionItem: IKeyValues = { key: 'participant_id', value: 'Action' };
  protected dataService: CompleterData;
  protected dataService2: CompleterData;
  public koordinatorId: number;
  public clientId: number;

  public table: IDynamicTable = {
    currentPage: 0,
    data: [],
    header: [
      { key: 'member_id', value: 'Member Id' },
      { key: 'first_name', value: 'Name' },
      { key: 'last_name', value: 'Last name' },
      { key: 'address', value: 'Address' },
      { key: 'participant_id', value: 'Action' }],
    pages: 0,
    pageSize: 10,
    filter: {}
  };

  constructor(private bservice: BiometricService, private completerService: CompleterService,
    private route: ActivatedRoute, private router: Router, private toast: ToastService) {

  }

  ngOnInit() {
    this.getClients(false);
    this.route.params.subscribe(params => {
      if (params['clientId']) {
        this.clientId = parseInt(params['clientId'], 10);
        if (!isNil(this.clientId)) {
          this.getClients(true);
          this.programDisabled = true;
        }
      }
      if (params['koordinatorId']) {
        this.koordinatorId = parseInt(params['koordinatorId'], 10);
      }
    });

    this.user = {
      pageSize: 10,
      page: 1
    };
  }


  public setParticipant(): void {
    const valid =
      !this.isNilOrEmpty(this.user.name)
      && !this.isNilOrEmpty(this.user.lastname)
      && !this.isNilOrEmpty(this.clientItem.id.toString())
      && !isNil(this.user.dob)
      && !isNil(this.user.gender);
    if (valid) {
      this.user.program = this.clientItem.program;
      this.bservice.setParticipant({
        first_name: this.user.name,
        last_name: this.user.lastname,
        client_id: this.clientItem.id.toString(),
        date_of_birth: this.user.dob,
        gender: this.user.gender
      }).subscribe((data: any) => {
        this.toast.success('Patient saved', 'Success');
      });
    } else {
      this.toast.error('client, lastname, firstname, date of birth, and gender are mandatory fields to add new patient', 'Error');
    }
  }

  private isNilOrEmpty(object: any) {
    return isNil(object) || object === '';
  }

  public clientChange(client: IClient) {
    this.user.program = client.program + ' - ' + new Date(client.reward_date).toLocaleDateString();
  }

  public getLastNames(event: any): void {
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

  public getFirstNames(event: any): void {
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
        this.dataService2 = this.completerService.local(this.firstNames, 'searchText', 'searchText');
      });
    }
  }

  public search(isValid: boolean): void {
    if (isValid) {
      this.user.client = (this.clientItem.id.toString() === '') ? null : this.clientItem.id.toString();
      this.user.program = this.clientItem.program;
      this.bservice.search(this.user).subscribe((data: IParticipantResult) => {
        this.table.data = data.items;
        this.pages = data.pages;
      });
    }
  }

  public notifyChangePage(page) {
    this.user.page = page;
    this.bservice.search(this.user).subscribe((data: IParticipantResult) => {
      this.table.data = data.items;
      this.pages = data.pages;
    });
  }

  public actionPerformed(id: number): void {
    if (!isNil(this.koordinatorId)) {
      this.bservice.bindPatientWithClient({
        id: id,
        kordinator_id: this.koordinatorId
      }).subscribe((data: any) => {
        this.toast.success('Patient binded', 'Success');
        this.router.navigate([`/biometrics/user/${id}`]);
      });
    } else {
      this.router.navigate([`/biometrics/user/${id}`]);
    }
  }

  private getClients(needSelection: boolean): void {
    this.bservice.getClients().subscribe((data: Array<IClient>) => {
      this.clients = data;
      if (needSelection) {
        const user: IClient = find(this.clients, (x: IClient) => x.id === this.clientId);
        this.clientItem = user;
        this.user.program = user.program + '-' + new Date(user.reward_date).toLocaleDateString();
      }
    });
  }

}
