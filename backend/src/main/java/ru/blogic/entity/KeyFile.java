package ru.blogic.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.annotation.Lazy;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
     * Сам файл
     */
    @Lob
    @NotNull
    private byte[] data;

    public KeyFile() {
    }

    public KeyFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public KeyFile(KeyFileDTO keyFileDTO) {
        this.id = keyFileDTO.getId();
        this.fileName = keyFileDTO.getFileName();
        this.fileType = keyFileDTO.getFileType();
//        this.data = keyFileDTO.getData();
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
                .append(data, keyFile.data)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
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
