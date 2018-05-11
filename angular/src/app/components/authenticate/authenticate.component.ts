import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import * as shajs from 'sha.js';
import { ActivatedRoute } from '@angular/router';
import { CommonService } from '../../shared/services/common.service';


@Component({
  selector: 'app-authenticate',
  templateUrl: './authenticate.component.html',
  styleUrls: ['./authenticate.component.css']
})
export class AuthenticateComponent implements OnInit {

  constructor(
    private commonService: CommonService
    , private route: ActivatedRoute
  ) { }
  private clientId = 'clientId';
  private token = 'token';
  private sk = 'sk';
  private patientId = 'patientId';
  private requestBy = 'requested-by';
  private nonce = 'nonce';
  private apiSig = 'api_sig';
  private headers: any = {};

  ngOnInit() {
    this.route.params.subscribe(params => {
      const requestBy = params['requestBy'];
      const token = params['token'];
      const nonce = params['nonce'];
      const sk = params['sk'];
      const signature = params['signature'];
      const client = params['client'];
      const patient = params['patient'];

      this.headers[this.token] = token;
      this.headers[this.nonce] = nonce;
      this.headers[this.requestBy] = requestBy;
      this.headers[this.sk] = sk;

      this.headers[this.apiSig] = signature;

      this.authenticate(client, patient);
    });
  }

  public authenticate(clientId, patientId): void {
    const key = '1234567887654321';
    const path = '/rest/authenticate';
    const d = new Date();
    const n = d.getTime();
    const requestBy = 'Koordinator-test';
    const nonce = '' + n;
    const token = 'V94aW0zBxc5gLpSvjQh0BVcfYN5l/QaL82e2NwpYzBU=';
    const sk = '' + n;

    if (clientId) {
      this.headers[this.clientId] = clientId;
    }

    if (patientId) {
      this.headers[this.patientId] = patientId;
    }
    this.request(this.headers);
  }

  public request(header: any): void {
    this.commonService.authenticate(header).subscribe((res: Response) => {

    });
  }

}
