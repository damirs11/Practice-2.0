package ru.blogic.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileStorageServiceTest {

    final String FILE_NAME = "mockFile";

    @Autowired
    FileStorageService fileStorageService;

    @Test
    void init() throws IOException {
        FileStorageService.init();
        assertTrue(Files.exists(FileStorageService.ROOT));
    }

    @Test
    void save() throws IOException {
        fileStorageService.save(new MockMultipartFile("data", FILE_NAME, MediaType.APPLICATION_OCTET_STREAM_VALUE, "some data".getBytes()));
        Path path = Paths.get(FileStorageService.ROOT + "/" + FILE_NAME);
        assertTrue(Files.exists(path));
    }

    @Test
    void deleteAll() throws IOException {
        fileStorageService.deleteAll();
        assertEquals(0, Objects.requireNonNull(FileStorageService.ROOT.toFile().list()).length);
    }

    @Test
    void destroy() throws IOException {
        FileStorageService.destroy();
        assertFalse(Files.exists(FileStorageService.ROOT));
    }
}