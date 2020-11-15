package ru.blogic.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.web.multipart.MultipartFile;

public class FilesDTO {

    private String key;

    private MultipartFile value;

    public FilesDTO() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MultipartFile getValue() {
        return value;
    }

    public void setValue(MultipartFile value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FilesDTO filesDTO = (FilesDTO) o;

        return new EqualsBuilder()
                .append(key, filesDTO.key)
                .append(value, filesDTO.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(key)
                .append(value)
                .toHashCode();
    }
}
