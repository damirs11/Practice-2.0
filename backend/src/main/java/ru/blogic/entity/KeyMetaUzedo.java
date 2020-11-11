package ru.blogic.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import ru.blogic.dto.KeyMetaUzedoDTO;
import ru.blogic.enums.LicenseType;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Мета данные ключа для ЮЗЭДО
 *
 * @author DSalikhov
 */
@Entity
@Table(name = "t_keys_uzedo")
public class KeyMetaUzedo {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Тип лицензии
     */
    @Column(name = "type")
    private LicenseType type;

    /**
     * Предыдущий Идентификатор лицензии
     */
    @Column(name = "previous_license")
    private UUID previousLicense;

    /**
     * Версия лицензии
     */
    @Column(name = "version")
    private String version;

    /**
     * Дата выпуска(формат дат yyyy-MM-dd)
     */
    @Column(name = "date_of_issue")
    private Date dateOfIssue;

    /**
     * Срок действия
     */
    @Column(name = "date_of_expiry")
    private Date dateOfExpiry;

    /**
     * Кому выдана
     */
    @Column(name = "issued_to")
    private String issuedTo;

    /**
     * Кем выдана
     */
    @Column(name = "issued_by")
    private String issuedBy;

    /**
     * Номер лицензии
     */
    @Column(name = "license_number")
    private String licenseNumber;

    /**
     * Список организаций( ИНН и КПП каждой организации разделяются ":" сами организации разделяются ";". Пример: ИНН,КПП;ИНН,КПП;... )
     */
    @Column(name = "organizations_list")
    private String organizationsList;

    /**
     * Дополнительно
     */
    @Column(name = "comment")
    private String comment;

    public KeyMetaUzedo() {
    }

    public KeyMetaUzedo(KeyMetaUzedoDTO keyMetaUzedoDTO) {
        this.id = keyMetaUzedoDTO.getId();
        this.type = keyMetaUzedoDTO.getType();
        this.previousLicense = keyMetaUzedoDTO.getPreviousLicense();
        this.version = keyMetaUzedoDTO.getVersion();
        this.dateOfIssue = keyMetaUzedoDTO.getDateOfIssue();
        this.dateOfExpiry = keyMetaUzedoDTO.getDateOfExpiry();
        this.issuedTo = keyMetaUzedoDTO.getIssuedTo();
        this.issuedBy = keyMetaUzedoDTO.getIssuedTo();
        this.licenseNumber = keyMetaUzedoDTO.getLicenseNumber();
        this.organizationsList = keyMetaUzedoDTO.getOrganizationsList();
        this.comment = keyMetaUzedoDTO.getComment();
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

        KeyMetaUzedo that = (KeyMetaUzedo) o;

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
