package ru.blogic.service;

import org.springframework.transaction.annotation.Transactional;
import ru.blogic.entity.Key;
import ru.blogic.entity.KeyFile;
import ru.blogic.repository.KeyFileRepository;
import ru.blogic.repository.KeyRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Date;

/**
 * Сервис для работы с ключами
 *
 * @author DSalikhov
 */
@Service
public class KeyService {

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
    public Iterable<Key> findAll() {
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
    public KeyFile getKeyFile(Long keyFileId) throws FileNotFoundException {
        return keyFileRepository.findById(keyFileId)
                .orElseThrow(() -> new FileNotFoundException("Key not found with id " + keyFileId));
    }

    /**
     * Создать новый ключ
     *
     * @param key входные данные для создания
     */
    @Transactional()
    public void createNewKey(Key key) {
        byte[] keyFile = fakeXMLSecurity(
                key.getName(),
                key.getExpiration(),
                key.getCoresCount(),
                key.getUsersCount(),
                key.getModuleFlags(),
                key.getKeyFileName()
        );

        KeyFile temp = new KeyFile();
        temp.setData(keyFile);
        temp.setFileName(key.getKeyFileName());
        temp.setFileType("text/plain"); //TODO: Изменить тип, как будет известен он
        temp.setKey(key);

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

