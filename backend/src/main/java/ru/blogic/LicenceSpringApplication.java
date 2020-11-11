package ru.blogic;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;
import ru.blogic.entity.Role;
import ru.blogic.enums.RoleNameImpl;
import ru.blogic.repository.RoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

/**
 * @author DSalikhov
 */
@SpringBootApplication
public class LicenceSpringApplication extends SpringBootServletInitializer {

    /**
     * Входная точка приложения
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(LicenceSpringApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LicenceSpringApplication.class);
    }
}
