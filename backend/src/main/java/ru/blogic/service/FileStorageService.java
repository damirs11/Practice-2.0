package ru.blogic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final Path root = Paths.get(System.getProperty("java.io.tmpdir") + "upload");
    private Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @PostConstruct
    public void init() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            logger.info("Ошибка при инициализации:", e);
        }
    }

    @Transactional
    public void save(MultipartFile file) throws IOException {
        Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
    }

    @Transactional
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }
}
