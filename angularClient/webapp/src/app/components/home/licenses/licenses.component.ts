import {Component, OnInit} from '@angular/core';
import {KeyParamsInput} from 'src/app/api/entity/keyParamsInput';
import {KeyService} from 'src/app/api/service/key/key.service';
import {LoggerService} from "../../../shared/logger/logger.service";

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

    refreshData() {
        this.keyService.getKeys().subscribe((res) => {
            this.logger.log(res);
            this.keys = res;
        });
    }

    downloadKey(keyFileId: number) {
        this.keyService.downloadKey(keyFileId).subscribe((_) => {
            this.logger.log('download' + keyFileId);
        });
    }
}
