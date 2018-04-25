import { Injectable } from '@angular/core';
import { ToastrService, ToastrConfig } from 'ngx-toastr';

@Injectable()
export class ToastService {

  public defaultMessage = 'Please wait...';
  public genericMessage = 'Error ocurred, pelase contact with your adminsitrator';
  constructor(public toastr: ToastrService) { }

  public error(msg: string, title: string): void {
    this.toastr.error(msg, title);
  }

  public success(msg: string, title: string): void {
    this.toastr.success(msg, title);
  }

}
