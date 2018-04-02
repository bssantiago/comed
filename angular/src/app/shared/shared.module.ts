import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { UserService } from './services/user.service';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { ModalModule } from 'ngx-bootstrap/modal';
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

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    CollapseModule.forRoot(),
    BsDropdownModule.forRoot(),
    TooltipModule.forRoot(),
    ModalModule.forRoot(),
    AngularReactDatesModule.forRoot(),
    FormsModule
  ],
  declarations: [
    NavbarComponent,
    FooterComponent,
    TableComponent,
    IncrementComponent,
    TagControlComponent,
    TagValidatorDirective
  ],
  providers: [UserService, CookieService],
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
    TagValidatorDirective
  ]
})
export class SharedModule { }
