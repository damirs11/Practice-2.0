package ru.blogic.controllers;

import ru.blogic.dto.response.MessageResponse;
import ru.blogic.entity.Key;
import ru.blogic.entity.KeyFile;
import ru.blogic.service.KeyService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

/**
 * Котроллер для работы с ключами
 *
 * @author DSalikhov
 */
@RestController
@RequestMapping("/api/key")
public class KeyController {

    private final KeyService keyService;

    public KeyController(KeyService keyService) {
        this.keyService = keyService;
    }

    /**
     * Достать все мета данные ключей
     *
     * @return ключи
     */
    @GetMapping("")
    public Iterable<Key> getAllKeys() {
        return keyService.findAll();
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
        KeyFile keyFile = keyService.getKeyFile(keyFileId);
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
    @PostMapping("/create")
    public MessageResponse createNewKey(@RequestBody Key key) {
        keyService.createNewKey(key);
        return new MessageResponse("Новый ключ создан");
    }
}
