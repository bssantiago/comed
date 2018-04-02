import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private httpClient: HttpClient) { }

  ngOnInit() {
  }

  testAuthenticate() {
    this.httpClient
      .post(`http://localhost:8081/comed/rest/authenticate`,  { withCredentials: true })
      .map((res: any) => {
        return res.result;
      }).subscribe(pepe => {
        console.log(pepe);
      });
  }

  testCookie() {
    this.httpClient
      .post(`http://localhost:8081/comed/rest/private/patients`, {}, { withCredentials: true })
      .map((res: any) => {
        return res.result;
      }).subscribe(pepe => {
        console.log(pepe);
      });
  }

}
