package ru.blogic.service;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    public static final Path ROOT = Paths.get(System.getProperty("java.io.tmpdir") + "upload");
    private Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @PostConstruct
    public void init() throws IOException {
        if (!Files.exists(ROOT)) {
            Files.createDirectory(ROOT);
        }
    }

    @Transactional
    public void save(MultipartFile file) throws IOException {
        Files.copy(file.getInputStream(), ROOT.resolve(file.getOriginalFilename()));
    }

    @Transactional
    public void deleteAll() {
        try {
            FileUtils.cleanDirectory(ROOT.toFile());
        } catch (IOException e) {
            logger.info("Ошибка при удаленнии файлов:", e);
        }
    }
}
