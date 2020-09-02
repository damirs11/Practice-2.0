package ru.blogic.converter;

import ru.blogic.enums.RoleNameImpl;

import javax.persistence.AttributeConverter;

public class RoleConverter implements AttributeConverter<RoleNameImpl, String> {
    @Override
    public String convertToDatabaseColumn(RoleNameImpl attribute) {
        return attribute.name();
    }

    @Override
    public RoleNameImpl convertToEntityAttribute(String dbData) {
        return RoleNameImpl.valueOf(dbData);
    }
}
