package ru.blogic.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.blogic.enums.LicenseType;

import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface KeyGenerator<KeyMeta, KeyFile> {

    /**
     * Имя сервиса
     *
     * @return имя
     */
    LicenseType getLicenseType();

    /**
     * Достать все мета данные ключей
     *
     * @return ключи
     */
    Page<KeyMeta> findAll(Pageable pageable);

    /**
     * Достать все мета данные ключей по типу лицензии
     *
     * @return ключи
     */
    Page<KeyMeta> findAllByType(Pageable pageable);

    /**
     * Скачать ключ
     *
     * @param keyFileId id ключа
     * @return ответ
     */
    @Transactional(readOnly = true)
    KeyFile getKeyFile(Long keyFileId) throws FileNotFoundException;

    /**
     * Создать новый ключ
     *
     * @param keyInputParams входные данные для создания
     */
    @Transactional()
    void generate(KeyMeta keyInputParams, @Nullable MultipartFile activationFile) throws IOException, InterruptedException;

}
