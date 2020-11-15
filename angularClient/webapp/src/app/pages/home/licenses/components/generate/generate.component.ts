import {Component, EventEmitter, OnInit} from '@angular/core';
import {LicenseType} from '@api/license/enums/license-type';
import {MatDialogRef} from '@angular/material/dialog';
import {FormDataType} from '@api/license/form-data-type';

@Component({
    selector: 'app-generate',
    templateUrl: './generate.component.html',
    styleUrls: ['./generate.component.less']
})
export class GenerateComponent implements OnInit {

    licenses = LicenseType;
    onGenerate: EventEmitter<FormDataType> = new EventEmitter<FormDataType>();
    selectedLicense: LicenseType;

    constructor(private dialogRef: MatDialogRef<GenerateComponent>) {
    }

    ngOnInit(): void {
    }

    generate($event: FormDataType): void {
        this.onGenerate.emit($event);
        this.dialogRef.close();
    }
}
