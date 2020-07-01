import { Component, OnInit } from '@angular/core';
import { ParamsInput } from 'src/app/api/entity/paramsInput';
import { KeyService } from 'src/app/api/service/key.service';

@Component({
  selector: 'app-licenses',
  templateUrl: './licenses.component.html',
  styleUrls: ['./licenses.component.less'],
})
export class LicensesComponent implements OnInit {
  data: ParamsInput[] = [];

  constructor(private keyService: KeyService) {}

  ngOnInit(): void {
    this.refreshData();
  }

  refreshData() {
    this.keyService.getKeys().subscribe((res) => {
      console.log(res);
      this.data = res;
    });
  }

  downloadKey(keyFileId: number) {
    console.log('download' + keyFileId);
    this.keyService.downloadKey(keyFileId);
  }
}
