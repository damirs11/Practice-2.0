package com.example;

import com.example.entity.Role;
import com.example.enums.RoleName;
import com.example.repository.RoleRepository;
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
public class Main {

    /**
     * Входная точка приложения
     *
     * Здесь создаются роли в базе данных
     *
     * @param args
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        RoleRepository roleRepository = context.getBean(RoleRepository.class);

        Arrays.stream(RoleName.values()).forEach(roleName -> {
            if (!roleRepository.findByName(roleName).isPresent()) {
                Role role = new Role(roleName);

                roleRepository.save(role);
            }
        });
    }
}
