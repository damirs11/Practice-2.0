package ru.blogic.entity;

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
public class Key {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Имя
     */
    private String name;

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
     *
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

    public Key() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public int hashCode() {
        return Objects.hash(getId(), getName(), getExpiration(), getCoresCount(), getUsersCount(), getModuleFlags(), getKeyFileName(), getComment());
    }
}
