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
     * Создает число основываясь на флагах
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

    /**
     * Создает флаги основываясь на числе
     *
     * @param moduleFlags
     */
    static convertByteToBooleanModuleFlag(moduleFlags: number): Array<boolean> {
        // tslint:disable-next-line:no-bitwise
        const bits = (n, b = 8) => [...Array(b)].map((x, i) => (n >> i) & 1);

        return bits(moduleFlags).slice(4).map(Boolean).reverse();
    }
}
