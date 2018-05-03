import { Component, OnInit } from '@angular/core';
import { find } from 'lodash';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { CommonService } from '../../services/common.service';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/observable/of';

export interface IPeople {
  name: string;
  id: string;
}

@Component({
  selector: 'app-autocomplete',
  templateUrl: './autocomplete.component.html',
  styleUrls: ['./autocomplete.component.css']
})
export class AutocompleteComponent implements OnInit {


  peopleList: Observable<Array<IPeople>>;
  selected = '';
  constructor(private nameServices: CommonService) { }

  private searchTerms = new Subject<string>();
  public ClientName = '';
  public flag = true;


  ngOnInit(): void {
    this.peopleList = this.searchTerms
      .debounceTime(300)        // esperar 300 mil segundos entre eventos
      .distinctUntilChanged()   // ignorar si la busqueda es igual a la anterior
      .switchMap(term => term   // cambiar siempre de observable
        ? this.nameServices.getPeople(term)
        // o devolver vacio
        : Observable.of<IPeople[]>([]))
      .catch(error => {
        // TODO: hacer manejo del error
        console.log(error);
        return Observable.of<any[]>([]);
      });
  }

  // mandar busqueda
  searchClient(term: string): void {
    this.flag = true;
    this.searchTerms.next(term);
  }

  onSelectPayaso(people) {
    console.log(people);
    if (people.id !== 0) {
      this.ClientName = people.name;
      this.flag = false;
    } else {
      return false;
    }
  }

}


