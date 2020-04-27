import { Component, OnInit } from '@angular/core';
import { ParamsInput } from 'src/app/api/entity/paramsInput';
import { KeyService } from 'src/app/api/service/key.service';
import { DomSanitizer } from '@angular/platform-browser';
import { HttpResponse } from '@angular/common/http';
import * as FileSaver from 'file-saver';

@Component({
  selector: 'app-licenses',
  templateUrl: './licenses.component.html',
  styleUrls: ['./licenses.component.less'],
})
export class LicensesComponent implements OnInit {
  data: ParamsInput[] = [];

  constructor(private keyService: KeyService) {}

  ngOnInit(): void {
    this.generateFakeData();
  }

  downloadKey(keyFileId: number) {
    console.log('download');
    // this.keyService.downloadKey(keyFileId);
  }

  private generateFakeData() {
    const key1 = new ParamsInput();
    key1.name = 'название1';
    key1.expiration = new Date();
    key1.coresCount = 4;
    key1.usersCount = 1;

    const key2 = new ParamsInput();
    key2.name = 'название2';
    key2.expiration = new Date();
    key2.coresCount = 2;
    key2.usersCount = 2;

    this.data.push(key1, key2);
  }
}
