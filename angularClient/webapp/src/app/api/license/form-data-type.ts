import {KeyGenerationParams} from '@api/license/key-generation-params';
import {LicenseType} from '@api/license/enums/license-type';

export class FormDataType {
    keyMeta: KeyGenerationParams;
    activationKeyFile: File;
    type: LicenseType;
}
