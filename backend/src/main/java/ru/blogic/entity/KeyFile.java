package ru.blogic.entity;

import ru.blogic.dto.KeyMetaDTO;

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

    public KeyFile(String fileName, String fileType, KeyMeta keyMeta, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.keyMeta = keyMeta;
        this.data = data;
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
}
