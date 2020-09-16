import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {tap} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {KeyGenerationParams} from '@api/license/key-generation-params';
import {LoggerService} from '@shared/service/logger/logger.service';
import {MessageResponse} from '@api/response/messageResponse';
import {GlobalConst} from '@shared/utils/global-const';
import {LicenseType} from '@api/license/enums/license-type';

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
    getKeys(licenseType: LicenseType): Observable<KeyGenerationParams[]> {
        return this.http.get<KeyGenerationParams[]>(`${GlobalConst.keyApi}`, {
            params: {
                type: licenseType
            }
        }).pipe(
            tap((_) => this.logger.log('Стягиваем ключи')),
        );
    }

    /**
     * Создает ключ основывая на введенных метаданных
     *
     * @param key - метаданные
     */
    createNewKey(key: FormData | KeyGenerationParams): Observable<MessageResponse> {
        return this.http.post<MessageResponse>(`${GlobalConst.keyApi}/create`, key).pipe(
            tap((_) => this.logger.log('Создаем новый ключ')),
        );
    }

    /**
     * Скачивает файл ключа
     *
     * @param keyFileId - id файла
     */
    downloadKey(keyFileId: number, selectedLicense: LicenseType): Observable<HttpResponse<Blob>> {
        return this.http
            .get(`${GlobalConst.keyApi}/download/${keyFileId}`, {
                params: {
                    type: selectedLicense
                },
                responseType: 'blob',
                observe: 'response',
            });
    }
}
