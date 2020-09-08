package ru.blogic.controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.blogic.dto.KeyFileDTO;
import ru.blogic.dto.KeyMetaDTO;
import ru.blogic.dto.response.MessageResponse;
import ru.blogic.interfaces.KeyGenerator;
import ru.blogic.service.FileStorageService;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Котроллер для работы с ключами
 *
 * @author DSalikhov
 */
@RestController
@RequestMapping("/api/key")
public class KeyController {

    private final KeyGenerator<KeyMetaDTO, KeyFileDTO> dummyKeyService;
    private final FileStorageService fileStorageService;

    public KeyController(KeyGenerator<KeyMetaDTO, KeyFileDTO> dummyKeyService, FileStorageService fileStorageService) {
        this.dummyKeyService = dummyKeyService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Достать все мета данные ключей
     *
     * @return ключи
     */
    @GetMapping("")
    public Iterable<KeyMetaDTO> getAllKeys() {
        return dummyKeyService.findAll();
    }

    /**
     * Скачать ключ
     *
     * @param keyFileId id ключа
     * @return ответ
     * @throws FileNotFoundException
     */
    @GetMapping("/download/{keyFileId:.+}")
    public ResponseEntity<Resource> downloadKeyFile(@PathVariable Long keyFileId) throws FileNotFoundException {
        KeyFileDTO keyFile = dummyKeyService.getKeyFile(keyFileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(keyFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", keyFile.getFileName()))
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .body(new ByteArrayResource(keyFile.getData()));
    }

    /**
     * Создать новый ключ
     *
     * @param key входные данные для создания
     * @return ответ
     */
    @PostMapping(value = "/create")
    public ResponseEntity createNewKey(@ModelAttribute KeyMetaDTO key) {
        try {
            dummyKeyService.generate(key);
            return ResponseEntity.ok(new MessageResponse("Новый ключ создан"));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Ошибка при генерации ключа"));
        }
    }
}
