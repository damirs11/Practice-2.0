package ru.blogic.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ru.blogic.entity.KeyMeta;
import ru.blogic.entity.Properties;
import ru.blogic.enums.LicenseType;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class KeyMetaDTO {

    /**
     * Идентификатор
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    /**
     * Тип лицензии
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LicenseType licenseType;

    /**
     * Предыдущий Идентификатор лицензии
     */
    private UUID previousLicense;

    /**
     * Дата выпуска(формат дат yyyy-MM-dd)
     */
    private Date dateOfIssue;

    /**
     * Срок действия
     */
    private Date dateOfExpiry;

    @JsonProperty("properties")
    private Map<String, String> properties;

    public KeyMetaDTO() {
    }

    public KeyMetaDTO(KeyMeta keyMeta) {
        this.id = keyMeta.getId();
        this.licenseType = keyMeta.getLicenseType();
        this.previousLicense = keyMeta.getPreviousLicense();
        this.dateOfIssue = keyMeta.getDateOfIssue();
        this.dateOfExpiry = keyMeta.getDateOfExpiry();
        this.properties = keyMeta.getProperties().stream().collect(Collectors.toMap(Properties::getKey, Properties::getValue));
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public UUID getPreviousLicense() {
        return previousLicense;
    }

    public void setPreviousLicense(UUID previousLicense) {
        this.previousLicense = previousLicense;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Date getDateOfExpiry() {
        return dateOfExpiry;
    }

    public void setDateOfExpiry(Date dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        KeyMetaDTO that = (KeyMetaDTO) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(licenseType, that.licenseType)
                .append(previousLicense, that.previousLicense)
                .append(dateOfIssue, that.dateOfIssue)
                .append(dateOfExpiry, that.dateOfExpiry)
                .append(properties, that.properties)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(licenseType)
                .append(previousLicense)
                .append(dateOfIssue)
                .append(dateOfExpiry)
                .append(properties)
                .toHashCode();
    }
}
