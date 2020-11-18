package ru.blogic.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.blogic.enums.LicenseType;

import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

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
    Page<KeyMeta> findAllByLicenseType(Pageable pageable);

    /**
     * Скачать ключ
     *
     * @param keyFileId id ключа
     * @return ответ
     */
    @Transactional(readOnly = true)
    KeyFile getKeyFile(UUID keyFileId) throws FileNotFoundException;

    /**
     * Создать новый ключ
     *
     * @param keyInputParams входные данные для создания
     * @param files
     */
    @Transactional()
    void generate(KeyMeta keyInputParams, @Nullable Map<String, MultipartFile> files) throws IOException, InterruptedException;

}
