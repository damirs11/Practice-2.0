import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest,} from '@angular/common/http';
import {EMPTY, Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AuthService} from '../service/auth/auth.service';
import {LoggerService} from "../../shared/logger/logger.service";
import {StatusCodes} from "http-status-codes/build/es";
import {Router} from "@angular/router";

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
    constructor(private authService: AuthService, private logger: LoggerService, private router: Router) {
    }

    intercept(
        req: HttpRequest<any>,
        next: HttpHandler
    ): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(
            catchError((error: HttpErrorResponse) => {
                if (error.error instanceof Error) {
                    this.logger.error(error);
                }

                if (error.status == StatusCodes.UNAUTHORIZED) {
                    this.router.navigate(['/auth/login']);
                } else if(error.status == StatusCodes.FORBIDDEN) {
                    this.router.navigate(['/error/403'])
                }

                return EMPTY;
            })
        );
    }
}
