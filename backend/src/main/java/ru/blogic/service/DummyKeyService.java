package ru.blogic.service;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.blogic.dto.KeyFileDTO;
import ru.blogic.dto.KeyMetaDTO;
import ru.blogic.entity.KeyFile;
import ru.blogic.entity.KeyMeta;
import ru.blogic.interfaces.KeyGenerator;
import ru.blogic.repository.KeyFileRepository;
import ru.blogic.repository.KeyRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class DummyKeyService implements KeyGenerator<KeyMetaDTO, KeyFileDTO> {

    private static final Logger logger = LoggerFactory.getLogger(DummyKeyService.class);
    private static final String PROPERTY_LICENSES_PATH = "LICENSES_PATH";

    private final Environment env;
    private final KeyRepository keyRepository;
    private final KeyFileRepository keyFileRepository;
    private final FileStorageService fileStorageService;

    public DummyKeyService(Environment env, KeyRepository keyRepository, KeyFileRepository keyFileRepository, FileStorageService fileStorageService) {
        this.env = env;
        this.keyRepository = keyRepository;
        this.keyFileRepository = keyFileRepository;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Достать все мета данные ключей
     *
     * @return ключи
     */
    @Override
    public Iterable<KeyMetaDTO> findAll() {
        return keyRepository.findAll().stream()
                .map(KeyMetaDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Скачать ключ
     *
     * @param keyFileId id ключа
     * @return ответ
     * @throws FileNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public KeyFileDTO getKeyFile(Long keyFileId) throws FileNotFoundException {
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
    public void generate(KeyMetaDTO keyMetaDTO) throws IOException {

        String pathToActivationFile = "nofile";

        if (keyMetaDTO.getFile() != null) {
            fileStorageService.save(keyMetaDTO.getFile());
            pathToActivationFile = FileStorageService.ROOT + File.separator + keyMetaDTO.getFile().getOriginalFilename();
        }

        Process processBuilder = new ProcessBuilder()
                .inheritIO()
                .command(
                        "java", "-jar", env.getProperty(PROPERTY_LICENSES_PATH),
                        keyMetaDTO.getName(),
                        keyMetaDTO.getExpiration().toString(),
                        String.valueOf(keyMetaDTO.getCoresCount()),
                        String.valueOf(keyMetaDTO.getUsersCount()),
                        String.valueOf(keyMetaDTO.getModuleFlags()),
                        keyMetaDTO.getKeyFileName(),
                        pathToActivationFile
                )
                .start();
        fileStorageService.deleteAll();

        File keyFileTemp = new File(System.getProperty("java.io.tmpdir") + keyMetaDTO.getKeyFileName());
        KeyMeta keyMeta = new KeyMeta(keyMetaDTO);
        KeyFile keyFile = new KeyFile(
                keyMetaDTO.getKeyFileName(),
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                keyMeta,
                FileUtils.readFileToByteArray(keyFileTemp)
        );

        this.keyFileRepository.save(keyFile);
    }
}
