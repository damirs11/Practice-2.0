package ru.blogic.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ru.blogic.entity.KeyMeta;
import ru.blogic.entity.KeyMetaUzedo;
import ru.blogic.enums.LicenseType;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * Мета данные ключа
 *
 * @author DSalikhov
 */
public class KeyMetaUzedoDTO {

    /**
     * Идентификатор
     */
    private UUID id;

    /**
     * Тип лицензии
     */
    @NotNull
    private LicenseType type;

    /**
     * Предыдущий Идентификатор лицензии
     */
    private UUID previousLicense;

    /**
     * Версия лицензии
     */
    @NotNull
    private String version;

    /**
     * Дата выпуска(формат дат yyyy-MM-dd)
     */
    @NotNull
    private Date dateOfIssue;

    /**
     * Срок действия
     */
    private Date dateOfExpiry;

    /**
     * Кому выдана
     */
    @NotNull
    private String issuedTo;

    /**
     * Кем выдана
     */
    @NotNull
    private String issuedBy;

    /**
     * Номер лицензии
     */
    @NotNull
    private String licenseNumber;

    /**
     * Список организаций( ИНН и КПП каждой организации разделяются ":" сами организации разделяются ";". Пример: ИНН,КПП;ИНН,КПП;... )
     */
    @NotNull
    private String organizationsList;

    /**
     * Дополнительно
     */
    @NotNull
    private String comment;

    public KeyMetaUzedoDTO() {
    }

    public KeyMetaUzedoDTO(KeyMetaUzedo KeyMetaUzedo) {
        this.id = KeyMetaUzedo.getId();
        this.type = KeyMetaUzedo.getType();
        this.previousLicense = KeyMetaUzedo.getPreviousLicense();
        this.version = KeyMetaUzedo.getVersion();
        this.dateOfIssue = KeyMetaUzedo.getDateOfIssue();
        this.dateOfExpiry = KeyMetaUzedo.getDateOfExpiry();
        this.issuedTo = KeyMetaUzedo.getIssuedTo();
        this.issuedBy = KeyMetaUzedo.getIssuedTo();
        this.licenseNumber = KeyMetaUzedo.getLicenseNumber();
        this.organizationsList = KeyMetaUzedo.getOrganizationsList();
        this.comment = KeyMetaUzedo.getComment();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LicenseType getType() {
        return type;
    }

    public void setType(LicenseType type) {
        this.type = type;
    }

    public UUID getPreviousLicense() {
        return previousLicense;
    }

    public void setPreviousLicense(UUID previousLicense) {
        this.previousLicense = previousLicense;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(String issuedTo) {
        this.issuedTo = issuedTo;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getOrganizationsList() {
        return organizationsList;
    }

    public void setOrganizationsList(String organizationsList) {
        this.organizationsList = organizationsList;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        KeyMetaUzedoDTO that = (KeyMetaUzedoDTO) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(type, that.type)
                .append(previousLicense, that.previousLicense)
                .append(version, that.version)
                .append(dateOfIssue, that.dateOfIssue)
                .append(dateOfExpiry, that.dateOfExpiry)
                .append(issuedTo, that.issuedTo)
                .append(issuedBy, that.issuedBy)
                .append(licenseNumber, that.licenseNumber)
                .append(organizationsList, that.organizationsList)
                .append(comment, that.comment)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(type)
                .append(previousLicense)
                .append(version)
                .append(dateOfIssue)
                .append(dateOfExpiry)
                .append(issuedTo)
                .append(issuedBy)
                .append(licenseNumber)
                .append(organizationsList)
                .append(comment)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE,
                true, true);
    }
}
