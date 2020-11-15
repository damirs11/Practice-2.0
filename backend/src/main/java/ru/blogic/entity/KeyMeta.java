package ru.blogic.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ru.blogic.dto.KeyMetaDTO;
import ru.blogic.enums.LicenseType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_keys_meta")
public class KeyMeta {

    /**
     * Идентификатор
     */
    @Id
    private UUID id;

    /**
     * Тип лицензии
     */
    @Column(name = "license_type")
    @NotNull
    private LicenseType licenseType;

    /**
     * Предыдущий Идентификатор лицензии
     */
    @Column(name = "previous_license")
    private UUID previousLicense;

    /**
     * Дата выпуска(формат дат yyyy-MM-dd)
     */
    @Column(name = "date_of_issue")
    @NotNull
    private Date dateOfIssue;

    /**
     * Срок действия
     */
    @Column(name = "date_of_expiry")
    private Date dateOfExpiry;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "key_meta_id")
    private List<Properties> properties;

    public KeyMeta() {
    }

    public KeyMeta(KeyMetaDTO keyMetaDTO) {
        this.id = keyMetaDTO.getId();
        this.licenseType = keyMetaDTO.getLicenseType();
        this.previousLicense = keyMetaDTO.getPreviousLicense();
        this.dateOfIssue = keyMetaDTO.getDateOfIssue();
        this.dateOfExpiry = keyMetaDTO.getDateOfExpiry();
        this.properties = keyMetaDTO.getProperties().entrySet().stream()
                .map(entry -> new Properties(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
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

    public List<Properties> getProperties() {
        return properties;
    }

    public void setProperties(List<Properties> properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        KeyMeta license = (KeyMeta) o;

        return new EqualsBuilder()
                .append(id, license.id)
                .append(licenseType, license.licenseType)
                .append(previousLicense, license.previousLicense)
                .append(dateOfIssue, license.dateOfIssue)
                .append(dateOfExpiry, license.dateOfExpiry)
                .append(properties, license.properties)
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE,
                true, true);
    }
}
