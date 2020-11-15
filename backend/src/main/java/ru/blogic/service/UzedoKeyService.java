package ru.blogic.service;

import javax0.license3j.Feature;
import javax0.license3j.License;
import javax0.license3j.crypto.LicenseKeyPair;
import javax0.license3j.io.KeyPairReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.blogic.dto.FilesDTO;
import ru.blogic.dto.KeyFileDTO;
import ru.blogic.dto.KeyMetaDTO;
import ru.blogic.entity.KeyFile;
import ru.blogic.entity.KeyMeta;
import ru.blogic.enums.LicenseType;
import ru.blogic.interfaces.KeyGenerator;
import ru.blogic.repository.KeyFileRepository;
import ru.blogic.repository.KeyRepository;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UzedoKeyService implements KeyGenerator<KeyMetaDTO, KeyFileDTO> {

    private static final Logger logger = LoggerFactory.getLogger(UzedoKeyService.class);
    private final KeyRepository keyRepository;
    private final KeyFileRepository keyFileRepository;
    private final FileStorageService fileStorageService;

    @Value("${JAVA_14_PATH}")
    private String JAVA_14_PATH;

    @Value("${PRIVATE_KEY_PATH}")
    private String PRIVATE_KEY_PATH;

    private final String DIGEST = "SHA-512";

    public UzedoKeyService(KeyRepository keyRepository, KeyFileRepository keyFileRepository, FileStorageService fileStorageService) {
        this.keyRepository = keyRepository;
        this.keyFileRepository = keyFileRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public LicenseType getLicenseType() {
        return LicenseType.UZEDO;
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
     * через переменную окружения спринга "LICENSE3JREPL_PATH"
     *
     * @param keyMetaDTO входные данные для создания
     */
    @Override
    @Transactional()
    public void generate(KeyMetaDTO keyMetaDTO, List<MultipartFile> files) throws IOException, InterruptedException {
        logger.info(System.getProperty("java.io.tmpdir"));
        logger.info(JAVA_14_PATH);
        logger.info(PRIVATE_KEY_PATH);

//        assert files != null;
//        fileStorageService.save(files.get(0));

        keyMetaDTO.setId(UUID.randomUUID());
        keyMetaDTO.setLicenseType(getLicenseType());

        License license = new License();
        license.add(Feature.Create.from("id:UUID=" + keyMetaDTO.getId()));
        if(keyMetaDTO.getPreviousLicense() != null) {
            license.add(Feature.Create.from("previousLicense:UUID=" + keyMetaDTO.getPreviousLicense()));
        }
        license.add(Feature.Create.dateFeature("dateOfIssue", keyMetaDTO.getDateOfIssue()));
        if(keyMetaDTO.getDateOfExpiry() != null) {
            license.add(Feature.Create.dateFeature("dateOfExpiry", keyMetaDTO.getDateOfExpiry()));
        }

        keyMetaDTO.getProperties().entrySet().forEach((property -> {
            license.add(Feature.Create.stringFeature(property.getKey(), property.getValue()));
        }));

        try (final KeyPairReader privateKeyReader = new KeyPairReader(PRIVATE_KEY_PATH)) {
            LicenseKeyPair privateKeyPair = privateKeyReader.readPrivate();
            license.sign(privateKeyPair.getPair().getPrivate(), DIGEST);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
            e.printStackTrace(); //TODO: Сделать нормальную обработку
        }

        keyMetaDTO.setLicenseType(getLicenseType());
        KeyFile keyFile = new KeyFile();
        keyFile.setData(license.serialized());
        keyFile.setFileName("UZEDO_LICENSE_" + new Date().toString()); //TODO: Добавить переменную с именем
        keyFile.setFileType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        keyFile.setKeyMeta(new KeyMeta(keyMetaDTO));
        this.keyFileRepository.save(keyFile);
    }
}
