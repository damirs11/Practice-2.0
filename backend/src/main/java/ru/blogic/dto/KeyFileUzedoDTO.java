package ru.blogic.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ru.blogic.entity.KeyFileUzedo;

/**
 * Файловое представление ключа
 *
 * @author DSalikhov
 */
public class KeyFileUzedoDTO {

    /**
     * ID
     */
    private Long id;

    /**
     * Имя
     */
    private String fileName;

    /**
     * Тип файла
     */
    private String fileType;

    /**
     * Мета данные ключа
     */
    private KeyMetaUzedoDTO keyMetaUzedoDTO;

    /**
     * Сам файл
     */
    private byte[] data;

    public KeyFileUzedoDTO() {
    }

    public KeyFileUzedoDTO(String fileName, String fileType, KeyMetaUzedoDTO keyMetaUzedoDTO, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.keyMetaUzedoDTO = keyMetaUzedoDTO;
        this.data = data;
    }

    public KeyFileUzedoDTO(KeyFileUzedo keyFileUzedo) {
        this.id = keyFileUzedo.getId();
        this.fileName = keyFileUzedo.getFileName();
        this.fileType = keyFileUzedo.getFileType();
        this.keyMetaUzedoDTO = new KeyMetaUzedoDTO(keyFileUzedo.getKeyMetaUzedo());
        this.data = keyFileUzedo.getData();
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

    public KeyMetaUzedoDTO getKeyMetaUzedoDTO() {
        return keyMetaUzedoDTO;
    }

    public void setKeyMetaUzedoDTO(KeyMetaUzedoDTO keyMetaUzedoDTO) {
        this.keyMetaUzedoDTO = keyMetaUzedoDTO;
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

        KeyFileUzedoDTO that = (KeyFileUzedoDTO) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(fileName, that.fileName)
                .append(fileType, that.fileType)
                .append(keyMetaUzedoDTO, that.keyMetaUzedoDTO)
                .append(data, that.data)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(fileName)
                .append(fileType)
                .append(keyMetaUzedoDTO)
                .append(data)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE,
                true, true);
    }
}
