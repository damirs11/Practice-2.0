import {Property} from '@api/license/property';

/**
 * Метаданные ключа, служит так и для генерации
 *
 * @author DSalikhov
 * @export
 */
export class KeyGenerationParams {

    /**
     * Идентификатор
     */
    id: string;

    /**
     * Предыдущий Идентификатор лицензии
     */
    previousLicense: string;

    /**
     * Дата выпуска(формат дат yyyy-MM-dd)
     */
    dateOfIssue: Date;

    /**
     * Срок действия
     */
    dateOfExpiry: Date;

    /**
     * Дополнительные параметры
     */
    properties: Map<string, string>;


    constructor(formData: any) {
        this.id = formData.id;
        this.previousLicense = formData.previousLicense;
        this.dateOfIssue = formData.dateOfIssue;
        this.dateOfExpiry = formData.dateOfExpiry;
        this.properties = formData.properties;
    }
}
