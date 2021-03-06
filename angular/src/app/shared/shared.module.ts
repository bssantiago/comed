import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { LocalStorageService } from './services/local-storage.service';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { ModalModule, BsModalRef } from 'ngx-bootstrap/modal';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { AngularReactDatesModule } from 'angular-react-dates';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { TableComponent } from './components/table/table.component';
import { IncrementComponent } from './components/increment/increment.component';
import { FormsModule } from '@angular/forms';
import { TagValidatorDirective } from './directives/tag-validator.directive';
import { CookieService } from 'ngx-cookie-service';
import { LoginCheckGuard } from './guards/login-check.guard';
import { RouterModule } from '@angular/router';
import { DynamicTableComponent } from './components/dynamic-table/dynamic-table.component';
import { RequestInterceptor } from './interceptors/request.interceptor';
import { ToastrModule } from 'ngx-toastr';
import { ComboDatepickerModule } from 'ngx-combo-datepicker';
import { DropdownDateComponent } from './components/dropdown-date/dropdown-date.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastService } from './services/toast.service';
import { CommonService } from './services/common.service';
import { AutocompleteComponent } from './components/autocomplete/autocomplete.component';

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
    ToastrModule.forRoot(),
    ComboDatepickerModule,
    BrowserAnimationsModule
  ],
  declarations: [
    NavbarComponent,
    FooterComponent,
    TableComponent,
    IncrementComponent,
    TagValidatorDirective,
    DynamicTableComponent,
    DropdownDateComponent,
    AutocompleteComponent
  ],
  providers: [
    CommonService,
    LocalStorageService,
    CookieService,
    LoginCheckGuard,
    BsModalRef,
    ToastService,
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
    TagValidatorDirective,
    DynamicTableComponent,
    DropdownDateComponent,
    AutocompleteComponent
  ]
})
export class SharedModule { }
