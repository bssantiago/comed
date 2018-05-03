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

  ngOnInit() {
    this.route.params.subscribe(params => {
      const client = params['client'];
      const patient = params['patient'];
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

    const headers: any = {};
    headers[this.token] = token;
    headers[this.nonce] = nonce;
    headers[this.requestBy] = requestBy;
    headers[this.sk] = sk;
    headers[this.requestBy] = requestBy;
    let signature = key + path;

    if (clientId) {
      signature = signature + this.clientId + clientId;
      headers[this.clientId] = clientId;
    }

    signature = signature + this.nonce + nonce;

    if (patientId) {
      signature = signature + this.patientId + patientId;
      headers[this.patientId] = patientId;
    }

    signature = signature + this.requestBy + requestBy;
    signature = signature + this.sk + sk;
    signature = signature + this.token + token;
    signature = shajs('sha256').update(signature).digest('hex');
    headers[this.apiSig] = signature;
    this.request(headers);
  }

  public request(header: any): void {
    this.commonService.authenticate(header).subscribe((res: Response) => {
      console.log(res);
    });
  }

}
