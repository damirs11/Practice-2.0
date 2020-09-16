package ru.blogic.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    public static final Path ROOT = Paths.get(System.getProperty("java.io.tmpdir") + "upload");

    @PostConstruct
    public static void init() throws IOException {
        if (!Files.exists(ROOT)) {
            Files.createDirectory(ROOT);
        }
    }

    @PreDestroy
    public static void destroy() throws IOException {
        if (Files.exists(ROOT)) {
            FileUtils.deleteDirectory(ROOT.toFile());
        }
    }

    @Transactional
    public void save(MultipartFile file) throws IOException {
        Files.copy(file.getInputStream(), ROOT.resolve(file.getOriginalFilename()));
    }

    @Transactional
    public void deleteAll() throws IOException {
        FileUtils.cleanDirectory(ROOT.toFile());
    }
}
