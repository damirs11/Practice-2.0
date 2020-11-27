import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {ErrorModalComponent} from '@components/modals/error-modal/error-modal.component';
import {GenerateComponent} from '@pages/home/licenses/components/generate/generate.component';
import {KeyGenerationParams} from '@api/license/key-generation-params';
import {LicenseType} from '@api/license/enums/license-type';

@Injectable({
    providedIn: 'root'
})
export class ModalService {

    constructor(
        public  dialog: MatDialog
    ) {
    }

    openErrorModal(message: string) {
        const dialogRef = this.dialog.open(ErrorModalComponent, {
            data: {
                message
            }
        });
    }

    openNewLicenseModal(generate, licenseTypeByDefault?: LicenseType, keyGenerationParams?: KeyGenerationParams) {
        const dialogRef = this.dialog.open(GenerateComponent, {
            data: {
                keyGenerationParams,
                licenseTypeByDefault
            }
        });
        dialogRef.componentInstance.onGenerate.subscribe(($event) => generate($event));
        return dialogRef;
    }
}
