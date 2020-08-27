import {ErrorHandler, Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoggerService {

  constructor(private errorHandler: ErrorHandler) {
  }

  log(value: any, ...rest: any[]): void {
    if (!environment.production) {
      console.log(value, ...rest);
    }
  }

  error(error: Error): void {
    this.errorHandler.handleError(error);
  }

  warn(value: any, ...rest: any[]): void {
    console.warn(value, ...rest);
  }
}
