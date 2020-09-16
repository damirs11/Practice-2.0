/**
 * Метаданные ключа, служит так и для генерации
 *
 * @author DSalikhov
 * @export
 */
export class KeyGenerationParams {

    /**
     * ID
     */
    id: number;

    /**
     * Имя
     */
    name: string;

    /**
     * Срок истечения
     */
    expiration: Date;

    /**
     * Количество ядер
     */
    coresCount: number;

    /**
     * Количество пользователей
     */
    usersCount: number;

    /**
     * Флаги
     * <br>
     * 00000000
     * <br>
     * Значение битов по порядку:
     * <br>
     * 1-платформа <br>
     * 2-СЭД <br>
     * 3-фичи (доп.возможности) <br>
     * 4-архив (2017), но его уже нет. <br>
     *
     * Типичная лицензия 11110000
     */
    moduleFlags: number;

    /**
     * Имя файла ключа
     */
    keyFileName: string;

    /**
     * Комментарий
     */
    comment: string;
}
