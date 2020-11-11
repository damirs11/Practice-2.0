/**
 * Метаданные ключа, служит так и для генерации
 *
 * @author DSalikhov
 * @export
 */
export class KeyGenerationUzedoParams {

    /**
     * Идентификатор
     */
    id: string;

    /**
     * Предыдущий Идентификатор лицензии
     */
    previousLicense: string;

    /**
     * Версия лицензии
     */
    version: string;

    /**
     * Дата выпуска(формат дат yyyy-MM-dd)
     */
    dateOfIssue: Date;

    /**
     * Срок действия
     */
    dateOfExpiry: Date | null;

    /**
     * Кому выдана
     */
    issuedTo: string;

    /**
     * Кем выдана
     */
    issuedBy: string;

    /**
     * Номер лицензии
     */
    licenseNumber: string;

    /**
     * Список организаций( ИНН и КПП каждой организации разделяются ":" сами организации разделяются ";". Пример: ИНН,КПП;ИНН,КПП;... )
     */
    organizationsList: string[];

    /**
     * Дополнительно
     */
    comment: string;
}
