package ru.blogic.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ru.blogic.dto.KeyFileDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Файловое представление ключа
 *
 * @author DSalikhov
 */
@Entity
@Table(name = "t_key_files")
public class KeyFile {

    /**
     * ID
     */
    @Id
    @NotNull
    private UUID id;

    /**
     * Имя
     */
    @Column(name = "file_name")
    @NotNull
    private String fileName;

    /**
     * Тип файла
     */
    @Column(name = "file_type")
    @NotNull
    private String fileType;

    /**
     * Мета данные ключа
     */
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @MapsId
    @NotNull
    private KeyMeta keyMeta;

    /**
     * Сам файл
     */
    @Lob
    @NotNull
    private byte[] data;

    public KeyFile() {
    }

    public KeyFile(String fileName, String fileType, KeyMeta keyMeta, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.keyMeta = keyMeta;
        this.data = data;
    }

    public KeyFile(KeyFileDTO keyFileDTO) {
        this.id = keyFileDTO.getId();
        this.fileName = keyFileDTO.getFileName();
        this.fileType = keyFileDTO.getFileType();
        this.keyMeta = new KeyMeta(keyFileDTO.getKeyMetaDTO());
        this.data = keyFileDTO.getData();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public KeyMeta getKeyMeta() {
        return keyMeta;
    }

    public void setKeyMeta(KeyMeta license) {
        this.keyMeta = license;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        KeyFile keyFile = (KeyFile) o;

        return new EqualsBuilder()
                .append(id, keyFile.id)
                .append(fileName, keyFile.fileName)
                .append(fileType, keyFile.fileType)
                .append(keyMeta, keyFile.keyMeta)
                .append(data, keyFile.data)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(fileName)
                .append(fileType)
                .append(keyMeta)
                .append(data)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE,
                true, true);
    }
}
