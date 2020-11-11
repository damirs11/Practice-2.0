import {KeyGenerationParams} from '@api/license/key-generation-params';
import {LicenseType} from '@api/license/enums/license-type';
import {KeyGenerationUzedoParams} from "@api/license/key-generation-uzedo-params";

export class FormDataUzedoType {
    keyMeta: KeyGenerationUzedoParams;
    publicKey: File;
    type: LicenseType;
}
