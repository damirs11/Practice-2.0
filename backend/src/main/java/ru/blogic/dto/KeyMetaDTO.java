package ru.blogic.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ru.blogic.entity.KeyMeta;
import ru.blogic.enums.LicenseType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * Мета данные ключа
 *
 * @author DSalikhov
 */
public class KeyMetaDTO {

    /**
     * ID
     */
    private Long id;

    /**
     * Тип лицензии
     */
    private LicenseType type;

    /**
     * Имя
     */
    @NotEmpty
    private String organization;

    /**
     * Дата истечения
     */
    @NotNull
    private Date expiration;

    /**
     * Количество ядер
     */
    @NotNull
    private int coresCount;

    /**
     * Количество пользователь
     */
    @NotNull
    private int usersCount;

    /**
     * Флаги
     * <br>
     * 00000000
     * <br>
     * Значение битов по порядку:
     * <br>
     * 1-платформа <br>
     * 2-СЭД <br>
     * 3-фичи (доп.возможности) <br>
     * 4-архив (2017), но его уже нет. <br>
     * <p>
     * Типичная лицензия 11110000
     */
    @NotNull
    private int moduleFlags;

    /**
     * Имя файла ключа
     */
    @NotEmpty
    private String keyFileName;

    /**
     * Комментарий
     */
    private String comment;

    public KeyMetaDTO() {
    }

    public KeyMetaDTO(KeyMeta keyMeta) {
        this.id = keyMeta.getId();
        this.type = keyMeta.getType();
        this.organization = keyMeta.getOrganization();
        this.expiration = keyMeta.getExpiration();
        this.coresCount = keyMeta.getCoresCount();
        this.usersCount = keyMeta.getUsersCount();
        this.moduleFlags = keyMeta.getModuleFlags();
        this.keyFileName = keyMeta.getKeyFileName();
        this.comment = keyMeta.getComment();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LicenseType getType() {
        return type;
    }

    public void setType(LicenseType type) {
        this.type = type;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public int getCoresCount() {
        return coresCount;
    }

    public void setCoresCount(int coresCount) {
        this.coresCount = coresCount;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public int getModuleFlags() {
        return moduleFlags;
    }

    public void setModuleFlags(int moduleFlags) {
        this.moduleFlags = moduleFlags;
    }

    public String getKeyFileName() {
        return keyFileName;
    }

    public void setKeyFileName(String keyFileName) {
        this.keyFileName = keyFileName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        KeyMetaDTO that = (KeyMetaDTO) o;

        return new EqualsBuilder()
                .append(coresCount, that.coresCount)
                .append(usersCount, that.usersCount)
                .append(moduleFlags, that.moduleFlags)
                .append(id, that.id)
                .append(type, that.type)
                .append(organization, that.organization)
                .append(expiration, that.expiration)
                .append(keyFileName, that.keyFileName)
                .append(comment, that.comment)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(type)
                .append(organization)
                .append(expiration)
                .append(coresCount)
                .append(usersCount)
                .append(moduleFlags)
                .append(keyFileName)
                .append(comment)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE,
                true, true);
    }
}
