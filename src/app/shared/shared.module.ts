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

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    CollapseModule.forRoot(),
    BsDropdownModule.forRoot(),
    TooltipModule.forRoot(),
    ModalModule.forRoot(),
    AngularReactDatesModule.forRoot()
  ],
  declarations: [
    NavbarComponent,
    FooterComponent,
    TableComponent
  ],
  providers: [UserService],
  exports: [
    CollapseModule,
    BsDropdownModule,
    TooltipModule,
    ModalModule,
    AngularReactDatesModule,
    NavbarComponent,
    FooterComponent,
    TableComponent
  ]
})
export class SharedModule { }
