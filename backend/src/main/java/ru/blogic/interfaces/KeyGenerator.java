package ru.blogic.interfaces;

import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.blogic.enums.LicenseType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public interface KeyGenerator<KeyMeta, KeyFile> {

    /**
     * Имя сервиса
     *
     * @return имя
     */
    LicenseType getName();

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
    public void generate(KeyMeta keyInputParams, @Nullable MultipartFile activationFile) throws IOException;

}
