import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {tap} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {KeyGenerationParams} from '@api/license/key-generation-params';
import {LoggerService} from '@shared/service/logger/logger.service';
import {MessageResponse} from '@api/response/messageResponse';
import {GlobalConst} from '@shared/utils/global-const';
import {LicenseType} from '@api/license/enums/license-type';
import {Page} from "@api/license/page";
import {PageSettings} from "@api/license/pageSettings";

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
    getKeys(licenseType: LicenseType): Observable<Page<KeyGenerationParams>> {
        if (licenseType === null) {
            return this.http.get<Page<KeyGenerationParams>>(`${GlobalConst.keyApi}`, {
                params: {
                    page: PageSettings.page.toString(),
                    size: PageSettings.size.toString()
                }
            }).pipe(
                tap((_) => this.logger.log('Стягиваем ключи')),
            );
        } else {
            return this.http.get<Page<KeyGenerationParams>>(`${GlobalConst.keyApi}`, {
                params: {
                    type: licenseType
                }
            }).pipe(
                tap((_) => this.logger.log('Стягиваем ключи')),
            );
        }

    }

    /**
     * Создает ключ основывая на введенных метаданных
     *
     * @param formData
     * @param licenseType
     */
    createNewKey(formData: FormData, licenseType: LicenseType): Observable<MessageResponse> {
        return this.http.post<MessageResponse>(`${GlobalConst.keyApi}/create`, formData, {
            params: {
                type: licenseType
            },
        }).pipe(
            tap((_) => this.logger.log('Создаем новый ключ')),
        );
    }

    /**
     * Скачивает файл ключа
     *
     * @param keyFileId - id файла
     * @param licenseType - тип лицензии
     */
    downloadKey(keyFileId: number, licenseType: LicenseType): Observable<HttpResponse<Blob>> {
        return this.http
            .get(`${GlobalConst.keyApi}/download/${keyFileId}`, {
                params: {
                    type: licenseType
                },
                responseType: 'blob',
                observe: 'response',
            });
    }
}
