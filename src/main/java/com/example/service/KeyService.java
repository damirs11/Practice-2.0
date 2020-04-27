package com.example.service;

import com.example.entity.Key;
import com.example.entity.KeyFile;
import com.example.repository.KeyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * The type Key service.
 */
@Service
public class KeyService {

    final KeyRepository keyRepository;

    public KeyService(KeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }

    public Iterable<Key> findAll() {
        return keyRepository.findAll();
    }

    public void createNewKey(Key key) {

        var keyFile = FakeXMLSecurity(
                key.getName(),
                key.getExpiration(),
                key.getCoresCount(),
                key.getUsersCount(),
                key.getModuleFlags(),
                key.getKeyFileName(),
                key.getActivationFile()
        );

        KeyFile temp = new KeyFile();
        temp.setData(keyFile);
        temp.setFileName(key.getKeyFileName());

        key.setKeyFile(temp);

        keyRepository.save(key);
    }

    private byte[] FakeXMLSecurity(String name,
                                   Date expiration,
                                   int coresCount,
                                   int usersCount,
                                   int moduleFlags,
                                   String keyFileName,
                                   MultipartFile activationFile) {
        int resultHashcode = name.hashCode() + expiration.hashCode() + coresCount + usersCount + moduleFlags + keyFileName.hashCode() + activationFile.hashCode();
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

