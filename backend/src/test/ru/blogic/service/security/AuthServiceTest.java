package ru.blogic.service.security;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
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
import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    HttpServletRequest httpServletRequest;

    @InjectMocks
    AuthService authService;

    @Test
    void authenticateUse_Success() throws ServletException {
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));

        assertEquals(
                new MessageResponse("Вход прошел успешно"),
                authService.authenticateUser(new LoginRequest("un", "pwd"), httpServletRequest)
        );
    }

    @Test
    void authenticateUse_WrongUsername() throws ServletException {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        assertEquals(
                new MessageResponse("Логин или пароль введины не правильно"),
                authService.authenticateUser(new LoginRequest("un", "pwd"), httpServletRequest)
        );
    }

    @Test
    void registerUser_Success() {
        Role role = new Role(RoleNameImpl.ROLE_USER);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        when(roleRepository.findByRole(any(RoleNameImpl.class))).thenReturn(Optional.of(role));

        when(userRepository.save(any(User.class))).thenReturn(null);

        assertEquals(
                new MessageResponse("Регистрация прошла успешно"),
                authService.registerUser(new RegistrationRequest("un", "pwd"))
        );
    }

    @Test
    void registerUser_UserExists() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(new User()));

        assertEquals(
                new MessageResponse("Имя уже занято"),
                authService.registerUser(new RegistrationRequest("un", "pwd"))
        );
    }

    @Test
    @WithMockUser("USER")
    void currentUser_User() {
        assertThat(
                authService.currentUser(),
                notNullValue()
        );
    }

    @Test
    @WithAnonymousUser
    void currentUser_AnonymousUser() {
        assertThat(
                authService.currentUser(),
                nullValue()
        );
    }
}