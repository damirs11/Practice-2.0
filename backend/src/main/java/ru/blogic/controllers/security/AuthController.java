package ru.blogic.controllers.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.blogic.dto.request.LoginRequest;
import ru.blogic.dto.request.RegistrationRequest;
import ru.blogic.service.AuthService;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> authenticateUser(HttpServletRequest request, @Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest, request));
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
