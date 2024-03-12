package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name_building")
    private String nameBuilding;

    @Column(name = "full_address_building")
    private String fullAddressBuilding;

    @Column(name = "number_housing_units")
    private String numberHousingUnits;

    @Column(name = "electricity_supplier")
    private String electricitySupplier;

    @Column(name = "hydro_quebec_contract_number")
    private String hydroQuebecContractNumber;

    @Column(name = "hydro_quebec_meter_number")
    private String hydroQuebecMeterNumber;

    @Column(name = "if_several_meters")
    private Boolean ifSeveralMeters;

    @Column(name = "specify_mete_intended")
    private String specifyMeteIntended;

    @Column(name = "hydro_quebec_account_number")
    private String hydroQuebecAccountNumber;

    @Column(name = "file_number")
    private String fileNumber;

    @Column(name = "type_buillding")
    private String typeBuillding;

    @Column(name = "nature_project")
    private String natureProject;

    @Column(name = "start_date_work")
    private Instant startDateWork;

    @Column(name = "end_date_work")
    private Instant endDateWork;

    @ManyToOne(fetch = FetchType.LAZY)
    private HydroQuebecRate hydroQuebecRate;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "projects")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "entreprise", "projects", "invoice" }, allowSetters = true)
    private Set<Service> services = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Project id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameBuilding() {
        return this.nameBuilding;
    }

    public Project nameBuilding(String nameBuilding) {
        this.setNameBuilding(nameBuilding);
        return this;
    }

    public void setNameBuilding(String nameBuilding) {
        this.nameBuilding = nameBuilding;
    }

    public String getFullAddressBuilding() {
        return this.fullAddressBuilding;
    }

    public Project fullAddressBuilding(String fullAddressBuilding) {
        this.setFullAddressBuilding(fullAddressBuilding);
        return this;
    }

    public void setFullAddressBuilding(String fullAddressBuilding) {
        this.fullAddressBuilding = fullAddressBuilding;
    }

    public String getNumberHousingUnits() {
        return this.numberHousingUnits;
    }

    public Project numberHousingUnits(String numberHousingUnits) {
        this.setNumberHousingUnits(numberHousingUnits);
        return this;
    }

    public void setNumberHousingUnits(String numberHousingUnits) {
        this.numberHousingUnits = numberHousingUnits;
    }

    public String getElectricitySupplier() {
        return this.electricitySupplier;
    }

    public Project electricitySupplier(String electricitySupplier) {
        this.setElectricitySupplier(electricitySupplier);
        return this;
    }

    public void setElectricitySupplier(String electricitySupplier) {
        this.electricitySupplier = electricitySupplier;
    }

    public String getHydroQuebecContractNumber() {
        return this.hydroQuebecContractNumber;
    }

    public Project hydroQuebecContractNumber(String hydroQuebecContractNumber) {
        this.setHydroQuebecContractNumber(hydroQuebecContractNumber);
        return this;
    }

    public void setHydroQuebecContractNumber(String hydroQuebecContractNumber) {
        this.hydroQuebecContractNumber = hydroQuebecContractNumber;
    }

    public String getHydroQuebecMeterNumber() {
        return this.hydroQuebecMeterNumber;
    }

    public Project hydroQuebecMeterNumber(String hydroQuebecMeterNumber) {
        this.setHydroQuebecMeterNumber(hydroQuebecMeterNumber);
        return this;
    }

    public void setHydroQuebecMeterNumber(String hydroQuebecMeterNumber) {
        this.hydroQuebecMeterNumber = hydroQuebecMeterNumber;
    }

    public Boolean getIfSeveralMeters() {
        return this.ifSeveralMeters;
    }

    public Project ifSeveralMeters(Boolean ifSeveralMeters) {
        this.setIfSeveralMeters(ifSeveralMeters);
        return this;
    }

    public void setIfSeveralMeters(Boolean ifSeveralMeters) {
        this.ifSeveralMeters = ifSeveralMeters;
    }

    public String getSpecifyMeteIntended() {
        return this.specifyMeteIntended;
    }

    public Project specifyMeteIntended(String specifyMeteIntended) {
        this.setSpecifyMeteIntended(specifyMeteIntended);
        return this;
    }

    public void setSpecifyMeteIntended(String specifyMeteIntended) {
        this.specifyMeteIntended = specifyMeteIntended;
    }

    public String getHydroQuebecAccountNumber() {
        return this.hydroQuebecAccountNumber;
    }

    public Project hydroQuebecAccountNumber(String hydroQuebecAccountNumber) {
        this.setHydroQuebecAccountNumber(hydroQuebecAccountNumber);
        return this;
    }

    public void setHydroQuebecAccountNumber(String hydroQuebecAccountNumber) {
        this.hydroQuebecAccountNumber = hydroQuebecAccountNumber;
    }

    public String getFileNumber() {
        return this.fileNumber;
    }

    public Project fileNumber(String fileNumber) {
        this.setFileNumber(fileNumber);
        return this;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getTypeBuillding() {
        return this.typeBuillding;
    }

    public Project typeBuillding(String typeBuillding) {
        this.setTypeBuillding(typeBuillding);
        return this;
    }

    public void setTypeBuillding(String typeBuillding) {
        this.typeBuillding = typeBuillding;
    }

    public String getNatureProject() {
        return this.natureProject;
    }

    public Project natureProject(String natureProject) {
        this.setNatureProject(natureProject);
        return this;
    }

    public void setNatureProject(String natureProject) {
        this.natureProject = natureProject;
    }

    public Instant getStartDateWork() {
        return this.startDateWork;
    }

    public Project startDateWork(Instant startDateWork) {
        this.setStartDateWork(startDateWork);
        return this;
    }

    public void setStartDateWork(Instant startDateWork) {
        this.startDateWork = startDateWork;
    }

    public Instant getEndDateWork() {
        return this.endDateWork;
    }

    public Project endDateWork(Instant endDateWork) {
        this.setEndDateWork(endDateWork);
        return this;
    }

    public void setEndDateWork(Instant endDateWork) {
        this.endDateWork = endDateWork;
    }

    public HydroQuebecRate getHydroQuebecRate() {
        return this.hydroQuebecRate;
    }

    public void setHydroQuebecRate(HydroQuebecRate hydroQuebecRate) {
        this.hydroQuebecRate = hydroQuebecRate;
    }

    public Project hydroQuebecRate(HydroQuebecRate hydroQuebecRate) {
        this.setHydroQuebecRate(hydroQuebecRate);
        return this;
    }

    public Set<Service> getServices() {
        return this.services;
    }

    public void setServices(Set<Service> services) {
        if (this.services != null) {
            this.services.forEach(i -> i.removeProject(this));
        }
        if (services != null) {
            services.forEach(i -> i.addProject(this));
        }
        this.services = services;
    }

    public Project services(Set<Service> services) {
        this.setServices(services);
        return this;
    }

    public Project addService(Service service) {
        this.services.add(service);
        service.getProjects().add(this);
        return this;
    }

    public Project removeService(Service service) {
        this.services.remove(service);
        service.getProjects().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return getId() != null && getId().equals(((Project) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", nameBuilding='" + getNameBuilding() + "'" +
            ", fullAddressBuilding='" + getFullAddressBuilding() + "'" +
            ", numberHousingUnits='" + getNumberHousingUnits() + "'" +
            ", electricitySupplier='" + getElectricitySupplier() + "'" +
            ", hydroQuebecContractNumber='" + getHydroQuebecContractNumber() + "'" +
            ", hydroQuebecMeterNumber='" + getHydroQuebecMeterNumber() + "'" +
            ", ifSeveralMeters='" + getIfSeveralMeters() + "'" +
            ", specifyMeteIntended='" + getSpecifyMeteIntended() + "'" +
            ", hydroQuebecAccountNumber='" + getHydroQuebecAccountNumber() + "'" +
            ", fileNumber='" + getFileNumber() + "'" +
            ", typeBuillding='" + getTypeBuillding() + "'" +
            ", natureProject='" + getNatureProject() + "'" +
            ", startDateWork='" + getStartDateWork() + "'" +
            ", endDateWork='" + getEndDateWork() + "'" +
            "}";
    }
}
