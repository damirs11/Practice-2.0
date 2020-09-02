import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {tap} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {KeyParamsInput} from '../../../../api/entity/keyParamsInput';
import {LoggerService} from '../../../../shared/logger/logger.service';
import {MessageResponse} from '../../../../api/entity/dto/response/messageResponse.model';

const apiUrl = 'api/key';

/**
 * Сервис для работы с ключами
 *
 * @author DSalikhov
 * @export
 */
@Injectable({
    providedIn: 'any'
})
export class KeyService {
    constructor(private http: HttpClient, private logger: LoggerService) {
    }

    /**
     * Возращает все ключи
     */
    getKeys(): Observable<KeyParamsInput[]> {
        return this.http.get<KeyParamsInput[]>(`${apiUrl}`).pipe(
            tap((_) => this.logger.log('Стягиваем ключи')),
        );
    }

    /**
     * Создает ключ основывая на введенных метаданных
     *
     * @param key - метаданные
     */
    createNewKey(key: KeyParamsInput): Observable<MessageResponse> {
        return this.http.post<MessageResponse>(`${apiUrl}/create`, key).pipe(
            tap((_) => this.logger.log('Создаем новый ключ')),
        );
    }

    /**
     * Скачивает файл ключа
     *
     * @param keyFileId - id файла
     */
    downloadKey(keyFileId: number): Observable<HttpResponse<Blob>> {
        return this.http
            .get(`${apiUrl}/download/${keyFileId}`, {
                responseType: 'blob',
                observe: 'response',
            });
    }
}
