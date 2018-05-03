import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { CommonService } from '../../services/common.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.less']
})
export class NavbarComponent implements OnInit {

  private hidden = false;

  constructor(
    private httpClient: HttpClient,
    private commonService: CommonService,
    private toastr: ToastrService,
    private router: Router) { }

  ngOnInit() {
    this.router.events.subscribe((val) => {
      this.hidden = this.router.url.indexOf('forbidden') >= 0 || this.router.url.indexOf('healthletter') >= 0;
    });
  }

  public exit(): void {
    this.commonService.exitPlatform().subscribe((data: any) => {
      window.close();
    }, (err: string) => {

    });
  }

}
