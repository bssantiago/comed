import { Component, OnInit } from '@angular/core';
import { IUserSearch } from '../../../shared/interfaces/user-info';

@Component({
  selector: 'app-biometric-search',
  templateUrl: './biometric-search.component.html',
  styleUrls: ['./biometric-search.component.css']
})
export class BiometricSearchComponent implements OnInit {


  public user: IUserSearch;
  constructor() { }

  ngOnInit() {
    this.user = {};
  }

  public search(model: IUserSearch, isValid: boolean): void {

  }

}
