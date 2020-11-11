package ru.blogic.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ru.blogic.dto.KeyFileDTO;
import ru.blogic.dto.KeyFileUzedoDTO;

import javax.persistence.*;

/**
 * Файловое представление ключа
 *
 * @author DSalikhov
 */
@Entity
@Table(name = "t_key_files_uzedo")
public class KeyFileUzedo {

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
    private KeyMetaUzedo keyMetaUzedo;

    /**
     * Сам файл
     */
    @Lob
    private byte[] data;

    public KeyFileUzedo() {
    }

    public KeyFileUzedo(String fileName, String fileType, KeyMetaUzedo keyMetaUzedo, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.keyMetaUzedo = keyMetaUzedo;
        this.data = data;
    }

    public KeyFileUzedo(KeyFileUzedoDTO keyFileUzedoDTO) {
        this.id = keyFileUzedoDTO.getId();
        this.fileName = keyFileUzedoDTO.getFileName();
        this.fileType = keyFileUzedoDTO.getFileType();
        this.keyMetaUzedo = new KeyMetaUzedo(keyFileUzedoDTO.getKeyMetaUzedoDTO());
        this.data = keyFileUzedoDTO.getData();
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

    public KeyMetaUzedo getKeyMetaUzedo() {
        return keyMetaUzedo;
    }

    public void setKeyMetaUzedo(KeyMetaUzedo keyMetaUzedo) {
        this.keyMetaUzedo = keyMetaUzedo;
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

        KeyFileUzedo that = (KeyFileUzedo) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(fileName, that.fileName)
                .append(fileType, that.fileType)
                .append(keyMetaUzedo, that.keyMetaUzedo)
                .append(data, that.data)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(fileName)
                .append(fileType)
                .append(keyMetaUzedo)
                .append(data)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE,
                true, true);
    }
}
