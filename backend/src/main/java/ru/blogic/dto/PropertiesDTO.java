package ru.blogic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ru.blogic.entity.Properties;

import java.util.UUID;

public class PropertiesDTO {

    private String key;

    private String value;

    public PropertiesDTO() {
    }

    public PropertiesDTO(Properties properties) {
        this.key = properties.getKey();
        this.value = properties.getValue();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PropertiesDTO that = (PropertiesDTO) o;

        return new EqualsBuilder()
                .append(key, that.key)
                .append(value, that.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(key)
                .append(value)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE,
                true, true);
    }
}
