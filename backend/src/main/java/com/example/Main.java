package com.example;

import com.example.entity.Role;
import com.example.enums.RoleName;
import com.example.repository.RoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        var context = SpringApplication.run(Main.class, args);

        context.getBean(RoleRepository.class).saveAll(
                Arrays.stream(RoleName.values()).map(roleName -> {
                    Role temp = new Role();
                    temp.setName(roleName);
                    return temp;
                }).collect(Collectors.toList())
        );
    }
}
