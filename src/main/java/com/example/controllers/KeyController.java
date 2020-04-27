package com.example.controllers;

import com.example.entity.Key;
import com.example.service.KeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createNewKey(@RequestBody Key key) {

        keyService.createNewKey(key);

        Map<String, String> model = new HashMap<>();
        model.put("message", "New key created");
        return ResponseEntity.ok(model);
    }
}
