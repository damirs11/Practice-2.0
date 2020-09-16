import {TestBed} from '@angular/core/testing';
import {AuthService} from './auth.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {MessageResponse} from '@api/response/messageResponse';
import {BaseAuth} from '../../../api/auth/base-auth';

describe('AuthService', () => {
    let service: AuthService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [AuthService]
        });

        service = TestBed.inject(AuthService);
        httpMock = TestBed.inject(HttpTestingController);
    });

    it('должен вернуть MessageResponse', () => {
        service.login(new BaseAuth('un', 'pwd')).subscribe((message: MessageResponse) => {
            expect(message.message).toBe('Вход прошел успешно');
        });

        const req = httpMock.expectOne(`api/auth/login`, 'вызов api');
        expect(req.request.method).toBe('POST');

        req.flush({
            message: 'Вход прошел успешно'
        });

        httpMock.verify();
    });
});
