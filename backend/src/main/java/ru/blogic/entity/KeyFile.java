package ru.blogic.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ru.blogic.dto.KeyFileDTO;
import ru.blogic.enums.LicenseType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Имя
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * Тип файла
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * Мета данные ключа
     */
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @MapsId
    private KeyMeta keyMeta;

    /**
     * Сам файл
     */
    @Lob
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public KeyMeta getKeyMeta() {
        return keyMeta;
    }

    public void setKeyMeta(KeyMeta keyMeta) {
        this.keyMeta = keyMeta;
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
