package ru.blogic.service;

import javax0.license3j.Feature;
import javax0.license3j.License;
import javax0.license3j.crypto.LicenseKeyPair;
import javax0.license3j.io.KeyPairReader;
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
import ru.blogic.dto.KeyMetaDTO;
import ru.blogic.entity.KeyFile;
import ru.blogic.entity.KeyMeta;
import ru.blogic.enums.LicenseType;
import ru.blogic.enums.TypeOfFile;
import ru.blogic.interfaces.KeyGenerator;
import ru.blogic.repository.KeyFileRepository;
import ru.blogic.repository.KeyRepository;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

@Service
public class UzedoKeyService implements KeyGenerator<KeyMetaDTO, KeyFileDTO> {

    private static final Logger logger = LoggerFactory.getLogger(UzedoKeyService.class);
    private final KeyRepository keyRepository;
    private final KeyFileRepository keyFileRepository;
    private final FileStorageService fileStorageService;

    @Value("${PRIVATE_KEY_PATH}")
    private String PRIVATE_KEY_PATH;

    private final String DIGEST = "SHA-512";
    private final List<String> exceptedProps = Collections.singletonList("licenseFileName");

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
    @Transactional(readOnly = true)
    public Page<KeyMetaDTO> findAll(Pageable pageable) {
        return keyRepository.findAll(pageable).map(KeyMetaDTO::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
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
     * @param files
     */
    @Override
    @Transactional()
    public KeyMetaDTO generate(KeyMetaDTO keyMetaDTO, Map<String, MultipartFile> files) throws IOException {
        keyMetaDTO.setId(UUID.randomUUID());
        keyMetaDTO.setDateOfIssue(new Date());
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

        keyMetaDTO.getProperties().entrySet().stream()
            .filter(property -> !exceptedProps.contains(property.getKey()))
            .forEach((property -> {
                license.add(Feature.Create.stringFeature(property.getKey(), property.getValue()));
            }));

        try (final KeyPairReader privateKeyReader = new KeyPairReader(PRIVATE_KEY_PATH)) {
            LicenseKeyPair privateKeyPair = privateKeyReader.readPrivate();
            license.sign(privateKeyPair.getPair().getPrivate(), DIGEST);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
            e.printStackTrace(); //TODO: Сделать нормальную обработку
        }

        List<KeyFile> filesToSave = new ArrayList<>();

        KeyFile licenseKeyFile = new KeyFile(
            keyMetaDTO.getProperties()
                    .getOrDefault(keyMetaDTO.getProperties().get(exceptedProps.get(0)),
                            "UZEDO_LICENSE_" + new Date().toString()),
            TypeOfFile.LICENSE_FILE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
            license.serialized()
        );
        filesToSave.add(licenseKeyFile);

        if ((files != null) && files.containsKey("publicKey")) {
            fileStorageService.save(files.get("publicKey"));
            KeyFile publicKey = new KeyFile(
                    files.get("publicKey").getOriginalFilename(),
                    TypeOfFile.PUBLIC_KEY,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    FileUtils.readFileToByteArray(new File(FileStorageService.ROOT + File.separator + files.get("publicKey").getOriginalFilename()))
            );
            filesToSave.add(publicKey);
        }


        KeyMeta keyMeta = new KeyMeta(keyMetaDTO);
        keyMeta.setFiles(filesToSave);

        this.keyRepository.save(keyMeta);

        return keyMetaDTO;
    }
}
