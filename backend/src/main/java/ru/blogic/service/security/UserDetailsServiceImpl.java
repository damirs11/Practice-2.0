package ru.blogic.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.blogic.repository.UserRepository;

/**
 * Реализация интерфейса UserDetailsService
 *
 * @author DSalikhov
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Проверяет наличие пользователя в базе данных
     *
     * @param username имя
     * @return данные пользователя
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional()
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователя с таким именем нет:" + username));
    }
}
