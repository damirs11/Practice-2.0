import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {ErrorModalComponent} from '@components/modals/error-modal/error-modal.component';
import {GenerateComponent} from '@pages/home/licenses/components/generate/generate.component';

@Injectable({
    providedIn: 'root'
})
export class ModalService {

    constructor(
        public  dialog: MatDialog
    ) {
    }

    openErrorModal(data: { error: string }) {
        const dialogRef = this.dialog.open(ErrorModalComponent, {data});
    }

    openNewLicenseModal(data, generate) {
        const dialogRef = this.dialog.open(GenerateComponent, {data});
        dialogRef.componentInstance.onGenerate.subscribe(($event) => generate($event));
    }
}
