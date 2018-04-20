import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { UserService } from './services/user.service';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { ModalModule, BsModalRef } from 'ngx-bootstrap/modal';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { AngularReactDatesModule } from 'angular-react-dates';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { TableComponent } from './components/table/table.component';
import { IncrementComponent } from './components/increment/increment.component';
import { TagControlComponent } from './components/tag-control/tag-control.component';
import { FormsModule } from '@angular/forms';
import { TagValidatorDirective } from './directives/tag-validator.directive';
import { CookieService } from 'ngx-cookie-service';
import { LoginCheckGuard } from './guards/login-check.guard';
import { RouterModule } from '@angular/router';
import { DynamicTableComponent } from './components/dynamic-table/dynamic-table.component';
import { RequestInterceptor } from './interceptors/request.interceptor';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    CollapseModule.forRoot(),
    BsDropdownModule.forRoot(),
    TooltipModule.forRoot(),
    ModalModule.forRoot(),
    AngularReactDatesModule.forRoot(),
    FormsModule,
    RouterModule,
    ToastrModule.forRoot()
  ],
  declarations: [
    NavbarComponent,
    FooterComponent,
    TableComponent,
    IncrementComponent,
    TagControlComponent,
    TagValidatorDirective,
    DynamicTableComponent
  ],
  providers: [UserService, CookieService, LoginCheckGuard, BsModalRef,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: RequestInterceptor,
      multi: true
    }],
  exports: [
    CollapseModule,
    BsDropdownModule,
    TooltipModule,
    ModalModule,
    AngularReactDatesModule,
    NavbarComponent,
    FooterComponent,
    TableComponent,
    IncrementComponent,
    TagControlComponent,
    TagValidatorDirective,
    DynamicTableComponent
  ]
})
export class SharedModule { }
