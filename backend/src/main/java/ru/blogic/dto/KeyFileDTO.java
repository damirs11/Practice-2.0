package ru.blogic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ru.blogic.entity.KeyFile;

import java.util.UUID;

/**
 * Файловое представление ключа
 *
 * @author DSalikhov
 */
public class KeyFileDTO {

    /**
     * ID
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

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
    private KeyMetaDTO keyMetaDTO;

    /**
     * Сам файл
     */
    private byte[] data;

    public KeyFileDTO() {
    }

    public KeyFileDTO(String fileName, String fileType, KeyMetaDTO keyMetaDTO, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.keyMetaDTO = keyMetaDTO;
        this.data = data;
    }

    public KeyFileDTO(KeyFile keyFile) {
        this.id = keyFile.getId();
        this.fileName = keyFile.getFileName();
        this.fileType = keyFile.getFileType();
        this.keyMetaDTO = new KeyMetaDTO(keyFile.getKeyMeta());
        this.data = keyFile.getData();
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

    public KeyMetaDTO getKeyMetaDTO() {
        return keyMetaDTO;
    }

    public void setKeyMetaDTO(KeyMetaDTO keyMetaDTO) {
        this.keyMetaDTO = keyMetaDTO;
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

        KeyFileDTO that = (KeyFileDTO) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(fileName, that.fileName)
                .append(fileType, that.fileType)
                .append(keyMetaDTO, that.keyMetaDTO)
                .append(data, that.data)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(fileName)
                .append(fileType)
                .append(keyMetaDTO)
                .append(data)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE,
                true, true);
    }
}
