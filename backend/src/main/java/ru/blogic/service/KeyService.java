package ru.blogic.service;

import org.springframework.transaction.annotation.Transactional;
import ru.blogic.dto.KeyFileDTO;
import ru.blogic.entity.KeyFile;
import ru.blogic.entity.KeyMetaDTO;
import ru.blogic.interfaces.KeyGenerator;
import ru.blogic.repository.KeyFileRepository;
import ru.blogic.repository.KeyRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Сервис для работы с ключами
 *
 * @author DSalikhov
 */
@Service
public class KeyService implements KeyGenerator<KeyMetaDTO, KeyFileDTO> {

    final KeyFileRepository keyFileRepository;

    final KeyRepository keyRepository;

    public KeyService(KeyFileRepository keyFileRepository, KeyRepository keyRepository) {
        this.keyFileRepository = keyFileRepository;
        this.keyRepository = keyRepository;
    }

    /**
     * Достать все мета данные ключей
     *
     * @return ключи
     */
    public Iterable<KeyMetaDTO> findAll() {
        return keyRepository.findAll();
    }

    /**
     * Скачать ключ
     *
     * @param keyFileId id ключа
     * @return ответ
     * @throws FileNotFoundException
     */
    @Transactional(readOnly = true)
    public KeyFileDTO getKeyFile(Long keyFileId) throws FileNotFoundException {
        return keyFileRepository.findById(keyFileId)
                .orElseThrow(() -> new FileNotFoundException("Ключ с id " + keyFileId + "не найден"));
    }

    /**
     * Создать новый ключ
     *
     * @param keyMetaDTO входные данные для создания
     */
    @Transactional()
    public void generate(KeyMetaDTO keyMetaDTO) {
        byte[] keyFile = fakeXMLSecurity(
                keyMetaDTO.getName(),
                keyMetaDTO.getExpiration(),
                keyMetaDTO.getCoresCount(),
                keyMetaDTO.getUsersCount(),
                keyMetaDTO.getModuleFlags(),
                keyMetaDTO.getKeyFileName()
        );

        KeyFileDTO temp = new KeyFileDTO();
        temp.setData(keyFile);
        temp.setFileName(keyMetaDTO.getKeyFileName());
        temp.setFileType("text/plain"); //TODO: Изменить тип, как будет известен он
        temp.setKeyMetaDTO(keyMetaDTO);

        this.keyFileRepository.save(temp);
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

