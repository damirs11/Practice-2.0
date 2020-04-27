package com.example.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Objects;

/**
 * The type Key.
 */
@Entity
@Table(name = "t_keys")
public class Key {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Date expiration;

    private int coresCount;

    private int usersCount;

    private int moduleFlags;

    private String keyFileName;

    private String comment;

    @Transient
    private MultipartFile activationFile;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private KeyFile keyFile;

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

    public MultipartFile getActivationFile() {
        return activationFile;
    }

    public void setActivationFile(MultipartFile activationFile) {
        this.activationFile = activationFile;
    }

    public KeyFile getKeyFile() {
        return keyFile;
    }

    public void setKeyFile(KeyFile keyFile) {
        this.keyFile = keyFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return getCoresCount() == key.getCoresCount() &&
                getUsersCount() == key.getUsersCount() &&
                getModuleFlags() == key.getModuleFlags() &&
                Objects.equals(getId(), key.getId()) &&
                Objects.equals(getName(), key.getName()) &&
                Objects.equals(getExpiration(), key.getExpiration()) &&
                Objects.equals(getKeyFileName(), key.getKeyFileName()) &&
                Objects.equals(getComment(), key.getComment()) &&
                Objects.equals(getActivationFile(), key.getActivationFile()) &&
                Objects.equals(getKeyFile(), key.getKeyFile());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getExpiration(), getCoresCount(), getUsersCount(), getModuleFlags(), getKeyFileName(), getComment(), getActivationFile(), getKeyFile());
    }
}
