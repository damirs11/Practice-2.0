import { Component, OnInit } from '@angular/core';

/**
 * Корневой компонент для безопасности
 *
 * @author DSalikhov
 * @export
 */
@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.less']
})
export class AuthComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
