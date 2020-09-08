export default class KeyUtils {
    /**
     * Достает имя файла из запроса
     *
     * @param res - запрос
     */
    static getFileNameFromResponse(res): string {
        const contentDispostion = res.headers.get('content-disposition');
        const matches = /filename="([^;]+)"/gi.exec(contentDispostion);
        const filename = (matches[1] || 'untitled').trim();
        return filename;
    }

    /**
     * Создает число основывая на флагах
     *
     * @param moduleFlags - массиво boolean
     */
    static convertBooleanModuleFlagToByte(moduleFlags: Array<boolean>): number {
        let byteFlag = 0b00000000;
        let bitCount = 8;

        for (const flag in moduleFlags) {
            bitCount--;
            if (moduleFlags[flag]) {
                byteFlag = byteFlag ^ (0b1 << bitCount);
            }
        }
        return byteFlag;
    }
}
