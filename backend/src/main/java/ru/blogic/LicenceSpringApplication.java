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
        SpringApplication.run(LicenceSpringApplication.class, args);
    }
}
