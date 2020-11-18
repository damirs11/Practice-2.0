import {Component, EventEmitter, Inject, OnInit} from '@angular/core';
import {LicenseType} from '@api/license/enums/license-type';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormDataType} from '@api/license/form-data-type';

export interface DialogData {
    licenseTypeByDefault: LicenseType;
}

@Component({
    selector: 'app-generate',
    templateUrl: './generate.component.html',
    styleUrls: ['./generate.component.less']
})
export class GenerateComponent {

    licenses = LicenseType;
    onGenerate: EventEmitter<FormDataType> = new EventEmitter<FormDataType>();
    selectedLicense: LicenseType;

    constructor(private dialogRef: MatDialogRef<GenerateComponent>, @Inject(MAT_DIALOG_DATA) public data: DialogData) {
        this.selectedLicense = data?.licenseTypeByDefault ?? null;
    }

    generate($event: FormDataType): void {
        this.onGenerate.emit($event);
        this.dialogRef.close();
    }
}
