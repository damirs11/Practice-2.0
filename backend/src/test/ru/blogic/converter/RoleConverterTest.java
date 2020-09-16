package ru.blogic.converter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.blogic.enums.RoleNameImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RoleConverterTest {

    RoleConverter roleConverter = new RoleConverter();

    @Test
    void convertToDatabaseColumn() {
        assertEquals(
                "ROLE_USER",
                roleConverter.convertToDatabaseColumn(RoleNameImpl.ROLE_USER)
        );
    }

    @Test
    void convertToEntityAttribute() {
        assertEquals(
                roleConverter.convertToEntityAttribute("ROLE_USER"),
                RoleNameImpl.ROLE_USER
        );
    }
}