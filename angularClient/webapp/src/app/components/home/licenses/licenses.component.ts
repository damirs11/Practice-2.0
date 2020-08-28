import {Component, OnInit} from '@angular/core';
import {KeyParamsInput} from 'src/app/api/entity/keyParamsInput';
import {KeyService} from 'src/app/api/service/key/key.service';
import {LoggerService} from '../../../shared/logger/logger.service';

/**
 * Компонент для логина
 *
 * @author DSalikhov
 * @export
 */
@Component({
    selector: 'app-licenses',
    templateUrl: './licenses.component.html',
    styleUrls: ['./licenses.component.less'],
})
export class LicensesComponent implements OnInit {
    keys: KeyParamsInput[] = [];

    constructor(private keyService: KeyService, private logger: LoggerService) {
    }

    ngOnInit(): void {
        this.refreshData();
    }

    /**
     * Обновляет данные о ключах
     */
    refreshData() {
        this.keyService.getKeys().subscribe((res) => {
            this.logger.log(res);
            this.keys = res;
        });
    }

    /**
     * Скачивает ключ
     *
     * @param keyFileId - id ключа
     */
    downloadKey(keyFileId: number) {
        this.keyService.downloadKey(keyFileId).subscribe((_) => {
            this.logger.log('download' + keyFileId);
        });
    }
}
