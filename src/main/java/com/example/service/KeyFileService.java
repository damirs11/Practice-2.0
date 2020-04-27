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
import java.util.Objects;

@Service
public class KeyFileService {

    final KeyFileRepository keyFileRepository;


    public KeyFileService(KeyFileRepository keyFileRepository, KeyRepository keyRepository) {
        this.keyFileRepository = keyFileRepository;
    }


    public KeyFile getKeyFile(Long keyFileId) throws FileNotFoundException {
        return keyFileRepository.findById(keyFileId)
                .orElseThrow(() -> new FileNotFoundException("Key not found with id " + keyFileId));
    }

    public KeyFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            KeyFile dbFile = new KeyFile(fileName, file.getContentType(), file.getBytes());

            return keyFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
