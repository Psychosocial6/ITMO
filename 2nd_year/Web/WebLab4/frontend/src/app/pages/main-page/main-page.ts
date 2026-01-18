import { Component } from '@angular/core';
import {PointForm} from './components/point-form/point-form';
import {Graph} from './components/graph/graph';
import {Table} from './components/table/table';

@Component({
  selector: 'app-main-page',
  imports: [
    PointForm,
    Graph,
    Table
  ],
  templateUrl: './main-page.html',
  styleUrl: './main-page.css',
})
export class MainPage {

}
