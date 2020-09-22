package ru.blogic.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ru.blogic.dto.KeyMetaDTO;
import ru.blogic.enums.LicenseType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * Мета данные ключа
 *
 * @author DSalikhov
 */
@Entity
@Table(name = "t_keys")
public class KeyMeta {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Тип лицензии
     */
    private LicenseType type;

    /**
     * Имя организации
     */
    private String organization;

    /**
     * Дата истечения
     */
    private Date expiration;

    /**
     * Количество ядер
     */
    @Column(name = "cores_count")
    private int coresCount;

    /**
     * Количество пользователь
     */
    @Column(name = "users_count")
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
    @Column(name = "module_flags")
    private int moduleFlags;

    /**
     * Имя файла ключа
     */
    @Column(name = "key_file_name")
    private String keyFileName;

    /**
     * Комментарий
     */
    private String comment;

    public KeyMeta() {
    }

    public KeyMeta(KeyMetaDTO keyMetaDTO) {
        this.id = keyMetaDTO.getId();
        this.type = keyMetaDTO.getType();
        this.organization = keyMetaDTO.getOrganization();
        this.expiration = keyMetaDTO.getExpiration();
        this.coresCount = keyMetaDTO.getCoresCount();
        this.usersCount = keyMetaDTO.getUsersCount();
        this.moduleFlags = keyMetaDTO.getModuleFlags();
        this.keyFileName = keyMetaDTO.getKeyFileName();
        this.comment = keyMetaDTO.getComment();
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

        KeyMeta keyMeta = (KeyMeta) o;

        return new EqualsBuilder()
                .append(coresCount, keyMeta.coresCount)
                .append(usersCount, keyMeta.usersCount)
                .append(moduleFlags, keyMeta.moduleFlags)
                .append(id, keyMeta.id)
                .append(type, keyMeta.type)
                .append(organization, keyMeta.organization)
                .append(expiration, keyMeta.expiration)
                .append(keyFileName, keyMeta.keyFileName)
                .append(comment, keyMeta.comment)
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
