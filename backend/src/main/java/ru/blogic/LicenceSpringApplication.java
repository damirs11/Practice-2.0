package ru.blogic;

import ru.blogic.entity.Role;
import ru.blogic.enums.RoleNameImpl;
import ru.blogic.repository.RoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

/**
 *
 *
 * @author DSalikhov
 */
@SpringBootApplication
public class LicenceSpringApplication {

    /**
     * Входная точка приложения
     *
     * Здесь создаются роли в базе данных
     *
     * @param args
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LicenceSpringApplication.class, args);
        RoleRepository roleRepository = context.getBean(RoleRepository.class);

        Arrays.stream(RoleNameImpl.values()).forEach(roles -> {
            if (!roleRepository.findByRole(roles).isPresent()) {
                Role role = new Role(roles);

                roleRepository.save(role);
            }
        });
    }
}
