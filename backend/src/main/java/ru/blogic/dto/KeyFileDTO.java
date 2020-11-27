package ru.blogic.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ru.blogic.entity.KeyFile;
import ru.blogic.enums.TypeOfFile;

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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private TypeOfFile typeOfFile;

    /**
     * Имя
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String fileName;

    /**
     * Тип файла
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String fileType;

    /**
     * Сам файл
     */
    @JsonIgnore
    private byte[] data;

    public KeyFileDTO() {
    }

    public KeyFileDTO(String fileName, TypeOfFile typeOfFile, String fileType, byte[] data) {
        this.fileName = fileName;
        this.typeOfFile = typeOfFile;
        this.fileType = fileType;
        this.data = data;
    }

    public KeyFileDTO(KeyFile keyFile) {
        this.id = keyFile.getId();
        this.typeOfFile = keyFile.getTypeOfFile();
        this.fileName = keyFile.getFileName();
        this.fileType = keyFile.getDataType();
        this.data = keyFile.getData();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TypeOfFile getTypeOfFile() {
        return typeOfFile;
    }

    public void setTypeOfFile(TypeOfFile typeOfFile) {
        this.typeOfFile = typeOfFile;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        KeyFileDTO that = (KeyFileDTO) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(typeOfFile, that.typeOfFile)
                .append(fileName, that.fileName)
                .append(fileType, that.fileType)
                .append(data, that.data)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(typeOfFile)
                .append(fileName)
                .append(fileType)
                .append(data)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE,
                true, true);
    }
}
