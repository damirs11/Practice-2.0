package ru.blogic.service;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.blogic.dto.KeyFileDTO;
import ru.blogic.dto.KeyFileUzedoDTO;
import ru.blogic.dto.KeyMetaDTO;
import ru.blogic.dto.KeyMetaUzedoDTO;
import ru.blogic.entity.KeyFile;
import ru.blogic.entity.KeyFileUzedo;
import ru.blogic.entity.KeyMeta;
import ru.blogic.entity.KeyMetaUzedo;
import ru.blogic.enums.LicenseType;
import ru.blogic.interfaces.KeyGenerator;
import ru.blogic.repository.KeyFileUzedoRepository;
import ru.blogic.repository.KeyUzedoRepository;

import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class UzedoKeyService implements KeyGenerator<KeyMetaUzedoDTO, KeyFileUzedoDTO> {

    private static final Logger logger = LoggerFactory.getLogger(UzedoKeyService.class);
    private final KeyUzedoRepository keyUzedoRepository;
    private final KeyFileUzedoRepository keyFileUzedoRepository;
    private final FileStorageService fileStorageService;

    @Value("${LICENSE3JREPL_PATH}")
    private String LICENSE3JREPL_PATH;

    @Value("${JAVA_14_PATH}")
    private String JAVA_14_PATH;

    @Value("${PRIVATE_KEY_PATH}")
    private String PRIVATE_KEY_PATH;

    public UzedoKeyService(KeyUzedoRepository keyUzedoRepository, KeyFileUzedoRepository keyFileUzedoRepository, FileStorageService fileStorageService) {
        this.keyUzedoRepository = keyUzedoRepository;
        this.keyFileUzedoRepository = keyFileUzedoRepository;
        this.fileStorageService = fileStorageService;
    }


    @Override
    public LicenseType getLicenseType() {
        return LicenseType.UZEDO;
    }

    @Override
    public Page<KeyMetaUzedoDTO> findAll(Pageable pageable) {
        return keyUzedoRepository.findAll(pageable).map(KeyMetaUzedoDTO::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<KeyMetaUzedoDTO> findAllByType(Pageable pageable) {
        return keyUzedoRepository.findAllByType(getLicenseType(), pageable).map(KeyMetaUzedoDTO::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public KeyFileUzedoDTO getKeyFile(Long keyFileId) throws FileNotFoundException {
        return keyFileUzedoRepository.findById(keyFileId)
                .map(KeyFileUzedoDTO::new)
                .orElseThrow(() -> new FileNotFoundException("Ключ с id " + keyFileId + "не найден"));
    }

    /**
     * Создает новый ключ при помощи вызова внешнего JAR к которому путь указывается
     * через переменную окружения спринга "LICENSE3JREPL_PATH"
     *
     * @param keyMetaDTO входные данные для создания
     */
    @Override
    @Transactional()
    public void generate(KeyMetaUzedoDTO keyMetaDTO, MultipartFile publicKey) throws IOException, InterruptedException {
        String pathToActivationFile = "nofile";
        logger.info(System.getProperty("java.io.tmpdir"));
        logger.info(System.getenv("JAVA_HOME"));
        logger.info(LICENSE3JREPL_PATH);

        logger.info("ГЕНЕРАЦИЯ ТЕСТ");
        logger.info("ГЕНЕРАЦИЯ ТЕСТ");
        logger.info("ГЕНЕРАЦИЯ ТЕСТ");
    }
}
