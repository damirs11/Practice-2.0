package com.example.controllers;

import com.example.entity.Key;
import com.example.entity.KeyFile;
import com.example.service.KeyService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/key")
public class KeyController {

    private final KeyService keyService;

    public KeyController(KeyService keyService) {
        this.keyService = keyService;
    }

    @GetMapping("")
    public Iterable<Key> getAllKeys() {
        return keyService.findAll();
    }

    @GetMapping("/download/{keyFileId:.+}")
    public ResponseEntity<Resource> downloadKeyFile(@PathVariable Long keyFileId, HttpServletRequest request) throws FileNotFoundException {
        KeyFile keyFile = keyService.getKeyFile(keyFileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(keyFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", keyFile.getFileName()))
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .body(new ByteArrayResource(keyFile.getData()));
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createNewKey(@RequestBody Key key) {

        keyService.createNewKey(key);

        Map<String, String> model = new HashMap<>();
        model.put("message", "New key created");
        return ResponseEntity.ok(model);
    }
}
