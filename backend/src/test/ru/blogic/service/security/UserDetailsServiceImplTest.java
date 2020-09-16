package ru.blogic.service.security;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.blogic.entity.User;
import ru.blogic.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_Success() {
        User user = new User();
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        assertEquals(
                user,
                userDetailsService.loadUserByUsername("un")
        );
    }

    @Test
    void loadUserByUsername_UsernameNotFoundException() {
        User user = new User();
        when(userRepository.findByUsername(any(String.class))).thenThrow(new UsernameNotFoundException("Пользователя с таким именем нет:" + user.getUsername()));

        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("un")
        );
    }
}