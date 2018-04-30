import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from '../../shared/services/local-storage.service';
import { CommonService } from '../../shared/services/common.service';

@Component({
  selector: 'app-forbidden',
  templateUrl: './forbidden.component.html',
  styleUrls: ['./forbidden.component.css']
})
export class ForbiddenComponent implements OnInit {
  public client: string = null;
  private path: string = null;
  constructor(private route: ActivatedRoute,
    private localStorageService: LocalStorageService,
    private commonService: CommonService) { }

  ngOnInit() {

    this.route.params.subscribe(params => {
      this.client = params['client'];
    });
  }

  public exit(): void {
    this.commonService.exitPlatform().subscribe((data: any) => {
      window.close();
    }, (err: string) => {

    });
  }

}
