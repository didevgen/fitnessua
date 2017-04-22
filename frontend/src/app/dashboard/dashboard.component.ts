import {Component} from '@angular/core';
import { Routes } from '@angular/router';

import {PAGES_MENU} from '../app.menu';
import {BaMenuService} from '../theme/';

@Component({
  selector: 'dashboard',
  styleUrls: ['./dashboard.scss'],
  templateUrl: './dashboard.html'
})
export class Dashboard {

  constructor(private _menuService: BaMenuService,) {
  }

  ngOnInit() {
    this._menuService.updateMenuByRoutes(<Routes>PAGES_MENU);
  }
}
