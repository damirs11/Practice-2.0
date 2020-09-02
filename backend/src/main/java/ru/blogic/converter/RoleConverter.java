package ru.blogic.converter;

import ru.blogic.enums.RoleNameImpl;
import ru.blogic.interfaces.RoleName;

import javax.persistence.AttributeConverter;

/**
 * Конвертор для внесения/вынесения ролей из БД
 */
public class RoleConverter implements AttributeConverter<RoleNameImpl, String> {

    /**
     * Достает название роли
     *
     * @param attribute Роль
     * @return Строка
     */
    @Override
    public String convertToDatabaseColumn(RoleNameImpl attribute) {
        return attribute.getAuthority();
    }

    /**
     * @param dbData Роль в ввиде строки
     * @return Роль из множесва
     */
    @Override
    public RoleNameImpl convertToEntityAttribute(String dbData) {
        return RoleNameImpl.valueOf(dbData);
    }
}
