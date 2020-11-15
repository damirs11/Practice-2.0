package ru.blogic.service;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.blogic.dto.FilesDTO;
import ru.blogic.dto.KeyFileDTO;
import ru.blogic.dto.KeyMetaDTO;
import ru.blogic.dto.PropertiesDTO;
import ru.blogic.entity.KeyFile;
import ru.blogic.entity.KeyMeta;
import ru.blogic.enums.LicenseType;
import ru.blogic.interfaces.KeyGenerator;
import ru.blogic.repository.KeyFileRepository;
import ru.blogic.repository.KeyRepository;

import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DummyKeyService implements KeyGenerator<KeyMetaDTO, KeyFileDTO> {

    private static final Logger logger = LoggerFactory.getLogger(DummyKeyService.class);
    private final KeyRepository keyRepository;
    private final KeyFileRepository keyFileRepository;
    private final FileStorageService fileStorageService;

    @Value("${LICENSE_PATH}")
    private String LICENSE_PATH;

    public DummyKeyService(KeyRepository keyRepository, KeyFileRepository keyFileRepository, FileStorageService fileStorageService) {
        this.keyRepository = keyRepository;
        this.keyFileRepository = keyFileRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public LicenseType getLicenseType() {
        return LicenseType.DUMMY;
    }

    @Override
    public Page<KeyMetaDTO> findAll(Pageable pageable) {
        return keyRepository.findAll(pageable).map(KeyMetaDTO::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<KeyMetaDTO> findAllByLicenseType(Pageable pageable) {
        return keyRepository.findAllByLicenseType(getLicenseType(), pageable).map(KeyMetaDTO::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public KeyFileDTO getKeyFile(UUID keyFileId) throws FileNotFoundException {
        return keyFileRepository.findById(keyFileId)
                .map(KeyFileDTO::new)
                .orElseThrow(() -> new FileNotFoundException("Ключ с id " + keyFileId + "не найден"));
    }

    /**
     * Создает новый ключ при помощи вызова внешнего JAR к которому путь указывается
     * через переменную окружения спринга "LICENSES_PATH"
     *
     * @param keyMetaDTO входные данные для создания
     */
    @Override
    @Transactional()
    public void generate(KeyMetaDTO keyMetaDTO, List<MultipartFile> files) throws IOException, InterruptedException {

        String pathToActivationFile = "nofile";
        logger.info(System.getProperty("java.io.tmpdir"));
        logger.info(System.getenv("JAVA_HOME"));
        logger.info(LICENSE_PATH);

        if (files != null) {
            fileStorageService.save(files.get(0));
            pathToActivationFile = FileStorageService.ROOT + File.separator + files.get(0).getOriginalFilename();
        }

        new ProcessBuilder()
                .inheritIO()
                .command(
                        "java", "-jar", LICENSE_PATH,
                        keyMetaDTO.getProperties().get("organizationName"),
                        keyMetaDTO.getDateOfExpiry().toString(),
                        keyMetaDTO.getProperties().get("coresCount"),
                        keyMetaDTO.getProperties().get("usersCount"),
                        keyMetaDTO.getProperties().get("moduleFlags"),
                        FileStorageService.ROOT + File.separator + keyMetaDTO.getProperties().get("keyFileName"),
                        pathToActivationFile
                )
                .start().waitFor();

        File keyFileTemp = new File(FileStorageService.ROOT + File.separator + keyMetaDTO.getProperties().get("keyFileName"));
        KeyMeta keyMeta = new KeyMeta(keyMetaDTO);
        keyMeta.setLicenseType(getLicenseType());
        KeyFile keyFile = new KeyFile(
                keyMetaDTO.getProperties().get("keyFileName"),
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                keyMeta,
                FileUtils.readFileToByteArray(keyFileTemp)
        );
        this.keyFileRepository.save(keyFile);
    }
}
