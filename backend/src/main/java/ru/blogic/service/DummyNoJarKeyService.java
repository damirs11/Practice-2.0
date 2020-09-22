package ru.blogic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.blogic.dto.KeyFileDTO;
import ru.blogic.dto.KeyMetaDTO;
import ru.blogic.entity.KeyFile;
import ru.blogic.enums.LicenseType;
import ru.blogic.interfaces.KeyGenerator;
import ru.blogic.repository.KeyFileRepository;
import ru.blogic.repository.KeyRepository;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Сервис для работы с ключами
 *
 * @author DSalikhov
 */
@Service
public class DummyNoJarKeyService implements KeyGenerator<KeyMetaDTO, KeyFileDTO> {

    final KeyFileRepository keyFileRepository;

    final KeyRepository keyRepository;

    public DummyNoJarKeyService(KeyFileRepository keyFileRepository, KeyRepository keyRepository) {
        this.keyFileRepository = keyFileRepository;
        this.keyRepository = keyRepository;
    }

    @Override
    public LicenseType getLicenseType() {
        return LicenseType.DUMMY_NO_JAR;
    }

    @Override
    public Page<KeyMetaDTO> findAll(Pageable pageable) {
        return keyRepository.findAll(pageable).map(KeyMetaDTO::new);
    }

    /**
     * Достать все мета данные ключей
     *
     * @return ключи
     */
    @Override
    public Page<KeyMetaDTO> findAllByType(Pageable pageable) {
        return keyRepository.findAllByType(getLicenseType(), pageable).map(KeyMetaDTO::new);
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
     * Создать новый ключ
     *
     * @param keyMetaDTO входные данные для создания
     */
    @Transactional()
    public void generate(KeyMetaDTO keyMetaDTO, MultipartFile activationFile) {
        byte[] keyFile = fakeXMLSecurity(
                keyMetaDTO.getOrganization(),
                keyMetaDTO.getExpiration(),
                keyMetaDTO.getCoresCount(),
                keyMetaDTO.getUsersCount(),
                keyMetaDTO.getModuleFlags(),
                keyMetaDTO.getKeyFileName()
        );

        keyMetaDTO.setType(getLicenseType());
        KeyFileDTO temp = new KeyFileDTO();
        temp.setData(keyFile);
        temp.setFileName(keyMetaDTO.getKeyFileName());
        temp.setFileType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        temp.setKeyMetaDTO(keyMetaDTO);

        this.keyFileRepository.save(new KeyFile(temp));
    }

    private byte[] fakeXMLSecurity(String name,
                                   Date expiration,
                                   int coresCount,
                                   int usersCount,
                                   int moduleFlags,
                                   String keyFileName) {
        int resultHashcode = name.hashCode() + expiration.hashCode() + coresCount + usersCount + moduleFlags + keyFileName.hashCode();
        return intToByteArray(resultHashcode);
    }

    private byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }
}

