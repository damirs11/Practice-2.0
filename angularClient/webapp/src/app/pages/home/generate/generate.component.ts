import {Component, EventEmitter, OnInit} from '@angular/core';
import {LicenseType} from '@api/license/enums/license-type';
import {KeyGenerationParams} from '@api/license/key-generation-params';
import {FormDataType} from '@api/license/form-data-type';
import {MatDialogRef} from '@angular/material/dialog';

@Component({
    selector: 'app-generate',
    templateUrl: './generate.component.html',
    styleUrls: ['./generate.component.less']
})
export class GenerateComponent implements OnInit {

    licenses = LicenseType;
    onGenerate: EventEmitter<any> = new EventEmitter<any>();
    selectedLicense: LicenseType;

    constructor(private dialogRef: MatDialogRef<GenerateComponent>) {
    }

    ngOnInit(): void {
    }

    generate($event: FormDataType): void {
        console.log('GenerateComponent generate');

        $event.type = this.selectedLicense;

        this.onGenerate.emit($event);
        this.dialogRef.close();
    }
}
