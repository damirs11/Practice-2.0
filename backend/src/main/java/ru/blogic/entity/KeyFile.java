package ru.blogic.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ru.blogic.dto.KeyFileDTO;
import ru.blogic.enums.TypeOfFile;

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

    @Column(name = "type_of_file")
    @Enumerated(EnumType.STRING)
    @NotNull
    private TypeOfFile typeOfFile;

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
    private String dataType;

    /**
     * Сам файл
     */
    @Lob
    @NotNull
    private byte[] data;

    public KeyFile() {
    }

    public KeyFile(String fileName, TypeOfFile typeOfFile, String dataType, byte[] data) {
        this.fileName = fileName;
        this.typeOfFile = typeOfFile;
        this.dataType = dataType;
        this.data = data;
    }

    public KeyFile(KeyFileDTO keyFileDTO) {
        this.id = keyFileDTO.getId();
        this.typeOfFile = keyFileDTO.getTypeOfFile();
        this.fileName = keyFileDTO.getFileName();
        this.dataType = keyFileDTO.getFileType();
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String fileType) {
        this.dataType = fileType;
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
                .append(typeOfFile, keyFile.typeOfFile)
                .append(fileName, keyFile.fileName)
                .append(dataType, keyFile.dataType)
                .append(data, keyFile.data)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(typeOfFile)
                .append(fileName)
                .append(dataType)
                .append(data)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE,
                true, true);
    }
}
