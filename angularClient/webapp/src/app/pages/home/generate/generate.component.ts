import {Component, OnInit} from '@angular/core';
import {LicenseType} from '@api/license/enums/license-type';
import {HomeFacade} from '@pages/home/shared/service/facade/home.facade';

@Component({
    selector: 'app-generate',
    templateUrl: './generate.component.html',
    styleUrls: ['./generate.component.less']
})
export class GenerateComponent implements OnInit {

    licenses = LicenseType;

    constructor(private homeFacade: HomeFacade) {
    }

    get selectedLicense() {
        return this.homeFacade.getSelectedLicense();
    }

    ngOnInit(): void {
    }

    onLicenseSelect(license: LicenseType) {
        this.homeFacade.setSelectedLicense(license);
    }

    generate(data: any) {
        this.homeFacade.createKey(data.keyMeta, data.activationKeyFile);
    }
}
