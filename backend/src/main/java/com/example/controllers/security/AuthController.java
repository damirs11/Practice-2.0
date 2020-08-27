package com.example.controllers.security;

import com.example.dto.request.LoginRequest;
import com.example.dto.request.RegistrationRequest;
import com.example.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Котроллер безопасноси
 *
 * @author DSalikhov
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Логин
     *
     * @param loginRequest сущность с данными для логина
     * @return ответ
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    /**
     * Регистация
     *
     * @param registrationRequest сущность с данными для регистации
     * @return ответ
     */
    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(authService.registerUser(registrationRequest));
    }

    /**
     * Возвращает данные о текущем пользователи в контексте
     *
     * @return данные пользователя
     */
    @GetMapping("/currentUser")
    public ResponseEntity<?> currentUser() {
        return ResponseEntity.ok(authService.currentUser());
    }
}
