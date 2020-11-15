import {KeyGenerationParams} from '@api/license/key-generation-params';
import {LicenseType} from '@api/license/enums/license-type';

export class FormDataType {
    keyMeta: KeyGenerationParams;
    files: File[];
    licenseType: LicenseType;

    constructor(formData: any) {
        this.keyMeta = new KeyGenerationParams(formData.keyMeta);
        this.files = formData.files;
        this.licenseType = formData.licenseType;
    }
}
