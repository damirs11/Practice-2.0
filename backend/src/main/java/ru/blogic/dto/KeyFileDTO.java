package ru.blogic.dto;

import ru.blogic.entity.KeyFile;

/**
 * Файловое представление ключа
 *
 * @author DSalikhov
 */
public class KeyFileDTO {

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
    private KeyMetaDTO keyMetaDTO;

    /**
     * Сам файл
     */
    private byte[] data;

    public KeyFileDTO() {
    }

    public KeyFileDTO(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public KeyFileDTO(KeyFile keyFile) {
        this.fileName = keyFile.getFileName();
        this.fileType = keyFile.getFileType();
        this.data = keyFile.getData();
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

    public KeyMetaDTO getKeyMetaDTO() {
        return keyMetaDTO;
    }

    public void setKeyMetaDTO(KeyMetaDTO keyMetaDTO) {
        this.keyMetaDTO = keyMetaDTO;
    }
}
