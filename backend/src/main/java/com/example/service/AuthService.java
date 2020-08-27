package com.example.service;

import com.example.dto.request.LoginRequest;
import com.example.dto.request.RegistrationRequest;
import com.example.dto.response.MessageResponse;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.enums.RoleName;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * Сервис авторизации
 *
 * @author DSalikhov
 */
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    /**
     * Логин
     *
     * @param loginRequest сущность с данными для логина
     * @return ответ
     */
    public MessageResponse authenticateUser(LoginRequest loginRequest) {
        if (!userRepository.existsByUsername(loginRequest.getUsername())) {
            return new MessageResponse("Логин или пароль введины не правильно");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new MessageResponse("Вход прошел успешно");
    }

    /**
     * Регистрация
     *
     * @param registrationRequest сущность с данными для регистации
     * @return ответ
     */
    public MessageResponse registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            return new MessageResponse("Имя уже занято");
        }

        User user = new User(
                registrationRequest.getUsername(),
                encoder.encode(registrationRequest.getPassword())
        );

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return new MessageResponse("Регистрация прошла успешно");
    }

    /**
     * Возвращает данные о текущем пользователи в контексте
     *
     * @return данные пользователя
     */
    public Object currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            return null;
        }
        return authentication.getPrincipal();
    }
}
