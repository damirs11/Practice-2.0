package ru.blogic.service.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.blogic.dto.request.LoginRequest;
import ru.blogic.dto.request.RegistrationRequest;
import ru.blogic.dto.response.MessageResponse;
import ru.blogic.entity.Role;
import ru.blogic.entity.User;
import ru.blogic.enums.RoleNameImpl;
import ru.blogic.repository.RoleRepository;
import ru.blogic.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * Сервис авторизации
 *
 * @author DSalikhov
 */
@Service
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
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
    public MessageResponse authenticateUser(LoginRequest loginRequest, HttpServletRequest request) {
        if (!userRepository.findByUsername(loginRequest.getUsername()).isPresent()) {
            return new MessageResponse("Логин или пароль введины не правильно");
        }
        try {
            request.login(loginRequest.getUsername(), loginRequest.getPassword());
        } catch (ServletException e) {
            return new MessageResponse("Вы уже вошли");
        }

        return new MessageResponse("Вход прошел успешно");
    }

    /**
     * Регистрация
     *
     * @param registrationRequest сущность с данными для регистации
     * @return ответ
     */
    public MessageResponse registerUser(RegistrationRequest registrationRequest) {
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            return new MessageResponse("Имя уже занято");
        }

        User user = new User(
                registrationRequest.getUsername(),
                encoder.encode(registrationRequest.getPassword())
        );

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByRole(RoleNameImpl.ROLE_USER)
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
