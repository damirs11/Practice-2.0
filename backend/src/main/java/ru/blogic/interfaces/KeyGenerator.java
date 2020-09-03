package ru.blogic.interfaces;

import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

public interface KeyGenerator<KeyMeta, KeyFile> {

    /**
     * Достать все мета данные ключей
     *
     * @return ключи
     */
    public Iterable<KeyMeta> findAll();

    /**
     * Скачать ключ
     *
     * @param keyFileId id ключа
     * @return ответ
     */
    @Transactional(readOnly = true)
    public KeyFile getKeyFile(Long keyFileId) throws FileNotFoundException;

    /**
     * Создать новый ключ
     *
     * @param keyInputParams входные данные для создания
     */
    @Transactional()
    public void generate(KeyMeta keyInputParams);

}
