import {Component, EventEmitter, Inject, OnInit} from '@angular/core';
import {LicenseType} from '@api/license/enums/license-type';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormDataType} from '@api/license/form-data-type';
import {BehaviorSubject} from 'rxjs';
import {KeyGenerationParams} from '@api/license/key-generation-params';

export interface DialogData {
    keyGenerationParams: KeyGenerationParams
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
    keyGenerationParams: BehaviorSubject<KeyGenerationParams> = new BehaviorSubject<KeyGenerationParams>(null);

    constructor(private dialogRef: MatDialogRef<GenerateComponent>, @Inject(MAT_DIALOG_DATA) public data: DialogData) {
        this.keyGenerationParams.next(data?.keyGenerationParams);
        this.selectedLicense = data?.licenseTypeByDefault ?? null;
    }

    generate($event: FormDataType): void {
        this.onGenerate.emit($event);
        // this.dialogRef.close();
    }

    clearForm(): void {
        this.keyGenerationParams.next(null);
    }
}
