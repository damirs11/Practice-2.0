package ru.blogic.controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.blogic.dto.FilesDTO;
import ru.blogic.dto.KeyFileDTO;
import ru.blogic.dto.KeyMetaDTO;
import ru.blogic.dto.response.MessageResponse;
import ru.blogic.enums.LicenseType;
import ru.blogic.interfaces.KeyGenerator;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Котроллер для работы с ключами
 *
 * @author DSalikhov
 */
@RestController
@RequestMapping("/api/key")
public class KeyController {

    final List<KeyGenerator<KeyMetaDTO, KeyFileDTO>> keyServices;

    public KeyController(List<KeyGenerator<KeyMetaDTO, KeyFileDTO>> keyServices) {
        this.keyServices = keyServices;
    }

    /**
     * Достать все мета данные ключей
     *
     * @return ключи
     */
    @GetMapping("")
    public ResponseEntity getAllKeys(@RequestParam(required = false) LicenseType licenseType, Pageable pageable) {

        if (licenseType == null) {
            return ResponseEntity.ok(keyServices.get(0).findAll(pageable));
        }

        for (KeyGenerator<KeyMetaDTO, KeyFileDTO> s : keyServices) {
            if (licenseType == s.getLicenseType()) {
                return ResponseEntity.ok(s.findAllByLicenseType(pageable));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Такого сервиса не существует"));
    }

    /**
     * Скачать ключ
     *
     * @param keyFileId id ключа
     * @return ответ
     */
    @GetMapping("/download/{keyFileId:.+}")
    public ResponseEntity downloadKeyFile(@PathVariable UUID keyFileId, @RequestParam LicenseType licenseType) {
        KeyFileDTO keyFile = null;
        for (KeyGenerator<KeyMetaDTO, KeyFileDTO> s : keyServices) {
            if (licenseType == s.getLicenseType()) {
                try {
                    keyFile = s.getKeyFile(keyFileId);
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(keyFile.getFileType()))
                            .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", keyFile.getFileName()))
                            .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                            .body(new ByteArrayResource(keyFile.getData()));
                } catch (FileNotFoundException e) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Такого ключа нет"));
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Такого сервиса не существует"));
    }

    /**
     * Создать новый ключ
     *
     * @param keyMetaDTO входные данные для создания
     * @return ответ
     */
    @PostMapping(value = "/create", consumes = {"multipart/form-data", "application/octet-stream"})
    @ResponseBody
    public ResponseEntity createNewKey(@PathParam("licenseType") LicenseType licenseType,
                                       @RequestPart("keyMeta") @Valid KeyMetaDTO keyMetaDTO,
                                       @RequestPart(value = "files", required = false) MultipartFile[] files) {

        Map<String, MultipartFile> filesMap = Arrays.stream(files).collect(Collectors.toMap(MultipartFile::getOriginalFilename, file -> file));

        for (KeyGenerator<KeyMetaDTO, KeyFileDTO> s : keyServices) {
            if (licenseType == s.getLicenseType()) {
                try {
                    return ResponseEntity.ok(s.generate(keyMetaDTO, filesMap));
                } catch (IOException | InterruptedException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Ошибка при генерации ключа"));
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Такого сервиса не существует"));
    }
}
