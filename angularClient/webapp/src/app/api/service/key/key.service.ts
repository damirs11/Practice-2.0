import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {saveAs} from 'file-saver';
import {KeyParamsInput} from '../../entity/keyParamsInput';
import {tap} from 'rxjs/operators';
import {LoggerService} from "../../../shared/logger/logger.service";
import {Observable, Subscription} from "rxjs";
import {MessageResponse} from "../../entity/dto/response/messageResponse.model";

const apiUrl = 'api/key';

@Injectable({
    providedIn: 'root',
})
export class KeyService {
    constructor(private http: HttpClient, private logger: LoggerService) {
    }

    getKeys(): Observable<KeyParamsInput[]> {
        return this.http.get<KeyParamsInput[]>(`${apiUrl}`).pipe(
            tap((_) => this.logger.log('Стягиваем ключи')),
        );
    }

    createNewKey(key: KeyParamsInput): Observable<MessageResponse> {
        return this.http.post<MessageResponse>(`${apiUrl}/create`, key).pipe(
            tap((_) => this.logger.log('Создаем новый ключ')),
        );
    }

    downloadKey(keyFileId: number): Observable<HttpResponse<Blob>> {
        return this.http
            .get(`${apiUrl}/download/${keyFileId}`, {
                responseType: 'blob',
                observe: 'response',
            }).pipe(
                tap((res: HttpResponse<Blob>) => {
                    const fileName = this.getFileNameFromResponse(res);
                    this.logger.log(fileName);
                    saveAs(res.body, fileName);
                })
            )
    }

    getFileNameFromResponse(res): string {
        const contentDispostion = res.headers.get('content-disposition');
        const matches = /filename="([^;]+)"/gi.exec(contentDispostion);
        const filename = (matches[1] || 'untitled').trim();
        return filename;
    }

    convertBooleanModuleFlagToByte(moduleFlags): number {
        let byteFlag = 0b00000000;
        let bitCount = 8;

        for (const flag in moduleFlags) {
            bitCount--;
            if (moduleFlags[flag]) {
                byteFlag = byteFlag ^ (0b1 << bitCount);
            }
        }
        return byteFlag;
    }
}
