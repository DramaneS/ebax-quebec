package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Entreprise.
 */
@Entity
@Table(name = "entreprise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_neq_number")
    private String companyNeqNumber;

    @Column(name = "is_thistax_exempt_organization")
    private Boolean isThistaxExemptOrganization;

    @Column(name = "resource_person")
    private String resourcePerson;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Entreprise id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Entreprise companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNeqNumber() {
        return this.companyNeqNumber;
    }

    public Entreprise companyNeqNumber(String companyNeqNumber) {
        this.setCompanyNeqNumber(companyNeqNumber);
        return this;
    }

    public void setCompanyNeqNumber(String companyNeqNumber) {
        this.companyNeqNumber = companyNeqNumber;
    }

    public Boolean getIsThistaxExemptOrganization() {
        return this.isThistaxExemptOrganization;
    }

    public Entreprise isThistaxExemptOrganization(Boolean isThistaxExemptOrganization) {
        this.setIsThistaxExemptOrganization(isThistaxExemptOrganization);
        return this;
    }

    public void setIsThistaxExemptOrganization(Boolean isThistaxExemptOrganization) {
        this.isThistaxExemptOrganization = isThistaxExemptOrganization;
    }

    public String getResourcePerson() {
        return this.resourcePerson;
    }

    public Entreprise resourcePerson(String resourcePerson) {
        this.setResourcePerson(resourcePerson);
        return this;
    }

    public void setResourcePerson(String resourcePerson) {
        this.resourcePerson = resourcePerson;
    }

    public String getAddress() {
        return this.address;
    }

    public Entreprise address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public Entreprise email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Entreprise phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entreprise)) {
            return false;
        }
        return getId() != null && getId().equals(((Entreprise) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entreprise{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", companyNeqNumber='" + getCompanyNeqNumber() + "'" +
            ", isThistaxExemptOrganization='" + getIsThistaxExemptOrganization() + "'" +
            ", resourcePerson='" + getResourcePerson() + "'" +
            ", address='" + getAddress() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
