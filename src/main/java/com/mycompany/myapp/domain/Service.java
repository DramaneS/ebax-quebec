package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Service.
 */
@Entity
@Table(name = "service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name_service")
    private String nameService;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "plans_all_floors_ventilation_plans")
    private Boolean plansAllFloorsVentilationPlans;

    @Column(name = "energy_simulation_report")
    private Boolean energySimulationReport;

    @Column(name = "windows_technical_sheet_and_u_factor")
    private Boolean windowsTechnicalSheetAndUFactor;

    @Column(name = "complete_wall_section")
    private Boolean completeWallSection;

    @Column(name = "brand_model_ventilation_devices")
    private Boolean brandModelVentilationDevices;

    @Column(name = "brand_model_veaters")
    private Boolean brandModelVeaters;

    @Column(name = "brand_model_hot_water_tanks")
    private Boolean brandModelHotWaterTanks;

    @Column(name = "brand_model_heat_pump_air_conditioning_units")
    private Boolean brandModelHeatPumpAirConditioningUnits;

    @Column(name = "type_lighting")
    private Boolean typeLighting;

    @Column(name = "quantity_each_model_and_dlc_product_id")
    private Boolean quantityEachModelAndDLCProductID;

    @Column(name = "checks_provide_technical_data_sheets")
    private Boolean checksProvideTechnicalDataSheets;

    @Column(name = "brand_model_plumbing_fixtures")
    private Boolean brandModelPlumbingFixtures;

    @Column(name = "other_relevant_devices_certification")
    private Boolean otherRelevantDevicesCertification;

    @Column(name = "heat_recovery_gray_water_solar_other")
    private Boolean heatRecoveryGrayWaterSolarOther;

    @ManyToOne(fetch = FetchType.LAZY)
    private Entreprise entreprise;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_service__project",
        joinColumns = @JoinColumn(name = "service_id"),
        inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "hydroQuebecRate", "services" }, allowSetters = true)
    private Set<Project> projects = new HashSet<>();

    @JsonIgnoreProperties(value = { "service" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "service")
    private Invoice invoice;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Service id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameService() {
        return this.nameService;
    }

    public Service nameService(String nameService) {
        this.setNameService(nameService);
        return this;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Service companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return this.phone;
    }

    public Service phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public Service email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getPlansAllFloorsVentilationPlans() {
        return this.plansAllFloorsVentilationPlans;
    }

    public Service plansAllFloorsVentilationPlans(Boolean plansAllFloorsVentilationPlans) {
        this.setPlansAllFloorsVentilationPlans(plansAllFloorsVentilationPlans);
        return this;
    }

    public void setPlansAllFloorsVentilationPlans(Boolean plansAllFloorsVentilationPlans) {
        this.plansAllFloorsVentilationPlans = plansAllFloorsVentilationPlans;
    }

    public Boolean getEnergySimulationReport() {
        return this.energySimulationReport;
    }

    public Service energySimulationReport(Boolean energySimulationReport) {
        this.setEnergySimulationReport(energySimulationReport);
        return this;
    }

    public void setEnergySimulationReport(Boolean energySimulationReport) {
        this.energySimulationReport = energySimulationReport;
    }

    public Boolean getWindowsTechnicalSheetAndUFactor() {
        return this.windowsTechnicalSheetAndUFactor;
    }

    public Service windowsTechnicalSheetAndUFactor(Boolean windowsTechnicalSheetAndUFactor) {
        this.setWindowsTechnicalSheetAndUFactor(windowsTechnicalSheetAndUFactor);
        return this;
    }

    public void setWindowsTechnicalSheetAndUFactor(Boolean windowsTechnicalSheetAndUFactor) {
        this.windowsTechnicalSheetAndUFactor = windowsTechnicalSheetAndUFactor;
    }

    public Boolean getCompleteWallSection() {
        return this.completeWallSection;
    }

    public Service completeWallSection(Boolean completeWallSection) {
        this.setCompleteWallSection(completeWallSection);
        return this;
    }

    public void setCompleteWallSection(Boolean completeWallSection) {
        this.completeWallSection = completeWallSection;
    }

    public Boolean getBrandModelVentilationDevices() {
        return this.brandModelVentilationDevices;
    }

    public Service brandModelVentilationDevices(Boolean brandModelVentilationDevices) {
        this.setBrandModelVentilationDevices(brandModelVentilationDevices);
        return this;
    }

    public void setBrandModelVentilationDevices(Boolean brandModelVentilationDevices) {
        this.brandModelVentilationDevices = brandModelVentilationDevices;
    }

    public Boolean getBrandModelVeaters() {
        return this.brandModelVeaters;
    }

    public Service brandModelVeaters(Boolean brandModelVeaters) {
        this.setBrandModelVeaters(brandModelVeaters);
        return this;
    }

    public void setBrandModelVeaters(Boolean brandModelVeaters) {
        this.brandModelVeaters = brandModelVeaters;
    }

    public Boolean getBrandModelHotWaterTanks() {
        return this.brandModelHotWaterTanks;
    }

    public Service brandModelHotWaterTanks(Boolean brandModelHotWaterTanks) {
        this.setBrandModelHotWaterTanks(brandModelHotWaterTanks);
        return this;
    }

    public void setBrandModelHotWaterTanks(Boolean brandModelHotWaterTanks) {
        this.brandModelHotWaterTanks = brandModelHotWaterTanks;
    }

    public Boolean getBrandModelHeatPumpAirConditioningUnits() {
        return this.brandModelHeatPumpAirConditioningUnits;
    }

    public Service brandModelHeatPumpAirConditioningUnits(Boolean brandModelHeatPumpAirConditioningUnits) {
        this.setBrandModelHeatPumpAirConditioningUnits(brandModelHeatPumpAirConditioningUnits);
        return this;
    }

    public void setBrandModelHeatPumpAirConditioningUnits(Boolean brandModelHeatPumpAirConditioningUnits) {
        this.brandModelHeatPumpAirConditioningUnits = brandModelHeatPumpAirConditioningUnits;
    }

    public Boolean getTypeLighting() {
        return this.typeLighting;
    }

    public Service typeLighting(Boolean typeLighting) {
        this.setTypeLighting(typeLighting);
        return this;
    }

    public void setTypeLighting(Boolean typeLighting) {
        this.typeLighting = typeLighting;
    }

    public Boolean getQuantityEachModelAndDLCProductID() {
        return this.quantityEachModelAndDLCProductID;
    }

    public Service quantityEachModelAndDLCProductID(Boolean quantityEachModelAndDLCProductID) {
        this.setQuantityEachModelAndDLCProductID(quantityEachModelAndDLCProductID);
        return this;
    }

    public void setQuantityEachModelAndDLCProductID(Boolean quantityEachModelAndDLCProductID) {
        this.quantityEachModelAndDLCProductID = quantityEachModelAndDLCProductID;
    }

    public Boolean getChecksProvideTechnicalDataSheets() {
        return this.checksProvideTechnicalDataSheets;
    }

    public Service checksProvideTechnicalDataSheets(Boolean checksProvideTechnicalDataSheets) {
        this.setChecksProvideTechnicalDataSheets(checksProvideTechnicalDataSheets);
        return this;
    }

    public void setChecksProvideTechnicalDataSheets(Boolean checksProvideTechnicalDataSheets) {
        this.checksProvideTechnicalDataSheets = checksProvideTechnicalDataSheets;
    }

    public Boolean getBrandModelPlumbingFixtures() {
        return this.brandModelPlumbingFixtures;
    }

    public Service brandModelPlumbingFixtures(Boolean brandModelPlumbingFixtures) {
        this.setBrandModelPlumbingFixtures(brandModelPlumbingFixtures);
        return this;
    }

    public void setBrandModelPlumbingFixtures(Boolean brandModelPlumbingFixtures) {
        this.brandModelPlumbingFixtures = brandModelPlumbingFixtures;
    }

    public Boolean getOtherRelevantDevicesCertification() {
        return this.otherRelevantDevicesCertification;
    }

    public Service otherRelevantDevicesCertification(Boolean otherRelevantDevicesCertification) {
        this.setOtherRelevantDevicesCertification(otherRelevantDevicesCertification);
        return this;
    }

    public void setOtherRelevantDevicesCertification(Boolean otherRelevantDevicesCertification) {
        this.otherRelevantDevicesCertification = otherRelevantDevicesCertification;
    }

    public Boolean getHeatRecoveryGrayWaterSolarOther() {
        return this.heatRecoveryGrayWaterSolarOther;
    }

    public Service heatRecoveryGrayWaterSolarOther(Boolean heatRecoveryGrayWaterSolarOther) {
        this.setHeatRecoveryGrayWaterSolarOther(heatRecoveryGrayWaterSolarOther);
        return this;
    }

    public void setHeatRecoveryGrayWaterSolarOther(Boolean heatRecoveryGrayWaterSolarOther) {
        this.heatRecoveryGrayWaterSolarOther = heatRecoveryGrayWaterSolarOther;
    }

    public Entreprise getEntreprise() {
        return this.entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Service entreprise(Entreprise entreprise) {
        this.setEntreprise(entreprise);
        return this;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Service projects(Set<Project> projects) {
        this.setProjects(projects);
        return this;
    }

    public Service addProject(Project project) {
        this.projects.add(project);
        return this;
    }

    public Service removeProject(Project project) {
        this.projects.remove(project);
        return this;
    }

    public Invoice getInvoice() {
        return this.invoice;
    }

    public void setInvoice(Invoice invoice) {
        if (this.invoice != null) {
            this.invoice.setService(null);
        }
        if (invoice != null) {
            invoice.setService(this);
        }
        this.invoice = invoice;
    }

    public Service invoice(Invoice invoice) {
        this.setInvoice(invoice);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Service)) {
            return false;
        }
        return getId() != null && getId().equals(((Service) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Service{" +
            "id=" + getId() +
            ", nameService='" + getNameService() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", plansAllFloorsVentilationPlans='" + getPlansAllFloorsVentilationPlans() + "'" +
            ", energySimulationReport='" + getEnergySimulationReport() + "'" +
            ", windowsTechnicalSheetAndUFactor='" + getWindowsTechnicalSheetAndUFactor() + "'" +
            ", completeWallSection='" + getCompleteWallSection() + "'" +
            ", brandModelVentilationDevices='" + getBrandModelVentilationDevices() + "'" +
            ", brandModelVeaters='" + getBrandModelVeaters() + "'" +
            ", brandModelHotWaterTanks='" + getBrandModelHotWaterTanks() + "'" +
            ", brandModelHeatPumpAirConditioningUnits='" + getBrandModelHeatPumpAirConditioningUnits() + "'" +
            ", typeLighting='" + getTypeLighting() + "'" +
            ", quantityEachModelAndDLCProductID='" + getQuantityEachModelAndDLCProductID() + "'" +
            ", checksProvideTechnicalDataSheets='" + getChecksProvideTechnicalDataSheets() + "'" +
            ", brandModelPlumbingFixtures='" + getBrandModelPlumbingFixtures() + "'" +
            ", otherRelevantDevicesCertification='" + getOtherRelevantDevicesCertification() + "'" +
            ", heatRecoveryGrayWaterSolarOther='" + getHeatRecoveryGrayWaterSolarOther() + "'" +
            "}";
    }
}
