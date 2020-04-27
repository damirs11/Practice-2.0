package com.example.service;

import com.example.entity.Key;
import com.example.entity.KeyFile;
import com.example.exception.FileStorageException;
import com.example.repository.KeyFileRepository;
import com.example.repository.KeyRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * The type Key service.
 */
@Service
public class KeyService {

    final KeyFileRepository keyFileRepository;
    final KeyRepository keyRepository;

    public KeyService(KeyFileRepository keyFileRepository, KeyRepository keyRepository) {
        this.keyFileRepository = keyFileRepository;
        this.keyRepository = keyRepository;
    }

    public Iterable<Key> findAll() {
        return keyRepository.findAll();
    }

    public KeyFile getKeyFile(Long keyFileId) throws FileNotFoundException {
        return keyFileRepository.findById(keyFileId)
                .orElseThrow(() -> new FileNotFoundException("Key not found with id " + keyFileId));
    }

    public void createNewKey(Key key) {

        var keyFile = FakeXMLSecurity(
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

    private byte[] FakeXMLSecurity(String name,
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

    public KeyFile fileToActivationFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {

            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            KeyFile activationFile = new KeyFile(fileName, file.getContentType(), file.getBytes());

            return activationFile;
        } catch (IOException e) {
            throw new FileStorageException(""); //TODO: переделать exception
        }
    }
}

