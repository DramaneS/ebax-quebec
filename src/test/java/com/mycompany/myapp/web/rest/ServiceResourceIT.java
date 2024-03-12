package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Service;
import com.mycompany.myapp.repository.ServiceRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ServiceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ServiceResourceIT {

    private static final String DEFAULT_NAME_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_NAME_SERVICE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PLANS_ALL_FLOORS_VENTILATION_PLANS = false;
    private static final Boolean UPDATED_PLANS_ALL_FLOORS_VENTILATION_PLANS = true;

    private static final Boolean DEFAULT_ENERGY_SIMULATION_REPORT = false;
    private static final Boolean UPDATED_ENERGY_SIMULATION_REPORT = true;

    private static final Boolean DEFAULT_WINDOWS_TECHNICAL_SHEET_AND_U_FACTOR = false;
    private static final Boolean UPDATED_WINDOWS_TECHNICAL_SHEET_AND_U_FACTOR = true;

    private static final Boolean DEFAULT_COMPLETE_WALL_SECTION = false;
    private static final Boolean UPDATED_COMPLETE_WALL_SECTION = true;

    private static final Boolean DEFAULT_BRAND_MODEL_VENTILATION_DEVICES = false;
    private static final Boolean UPDATED_BRAND_MODEL_VENTILATION_DEVICES = true;

    private static final Boolean DEFAULT_BRAND_MODEL_VEATERS = false;
    private static final Boolean UPDATED_BRAND_MODEL_VEATERS = true;

    private static final Boolean DEFAULT_BRAND_MODEL_HOT_WATER_TANKS = false;
    private static final Boolean UPDATED_BRAND_MODEL_HOT_WATER_TANKS = true;

    private static final Boolean DEFAULT_BRAND_MODEL_HEAT_PUMP_AIR_CONDITIONING_UNITS = false;
    private static final Boolean UPDATED_BRAND_MODEL_HEAT_PUMP_AIR_CONDITIONING_UNITS = true;

    private static final Boolean DEFAULT_TYPE_LIGHTING = false;
    private static final Boolean UPDATED_TYPE_LIGHTING = true;

    private static final Boolean DEFAULT_QUANTITY_EACH_MODEL_AND_DLC_PRODUCT_ID = false;
    private static final Boolean UPDATED_QUANTITY_EACH_MODEL_AND_DLC_PRODUCT_ID = true;

    private static final Boolean DEFAULT_CHECKS_PROVIDE_TECHNICAL_DATA_SHEETS = false;
    private static final Boolean UPDATED_CHECKS_PROVIDE_TECHNICAL_DATA_SHEETS = true;

    private static final Boolean DEFAULT_BRAND_MODEL_PLUMBING_FIXTURES = false;
    private static final Boolean UPDATED_BRAND_MODEL_PLUMBING_FIXTURES = true;

    private static final Boolean DEFAULT_OTHER_RELEVANT_DEVICES_CERTIFICATION = false;
    private static final Boolean UPDATED_OTHER_RELEVANT_DEVICES_CERTIFICATION = true;

    private static final Boolean DEFAULT_HEAT_RECOVERY_GRAY_WATER_SOLAR_OTHER = false;
    private static final Boolean UPDATED_HEAT_RECOVERY_GRAY_WATER_SOLAR_OTHER = true;

    private static final String ENTITY_API_URL = "/api/services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServiceRepository serviceRepository;

    @Mock
    private ServiceRepository serviceRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceMockMvc;

    private Service service;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Service createEntity(EntityManager em) {
        Service service = new Service()
            .nameService(DEFAULT_NAME_SERVICE)
            .companyName(DEFAULT_COMPANY_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .plansAllFloorsVentilationPlans(DEFAULT_PLANS_ALL_FLOORS_VENTILATION_PLANS)
            .energySimulationReport(DEFAULT_ENERGY_SIMULATION_REPORT)
            .windowsTechnicalSheetAndUFactor(DEFAULT_WINDOWS_TECHNICAL_SHEET_AND_U_FACTOR)
            .completeWallSection(DEFAULT_COMPLETE_WALL_SECTION)
            .brandModelVentilationDevices(DEFAULT_BRAND_MODEL_VENTILATION_DEVICES)
            .brandModelVeaters(DEFAULT_BRAND_MODEL_VEATERS)
            .brandModelHotWaterTanks(DEFAULT_BRAND_MODEL_HOT_WATER_TANKS)
            .brandModelHeatPumpAirConditioningUnits(DEFAULT_BRAND_MODEL_HEAT_PUMP_AIR_CONDITIONING_UNITS)
            .typeLighting(DEFAULT_TYPE_LIGHTING)
            .quantityEachModelAndDLCProductID(DEFAULT_QUANTITY_EACH_MODEL_AND_DLC_PRODUCT_ID)
            .checksProvideTechnicalDataSheets(DEFAULT_CHECKS_PROVIDE_TECHNICAL_DATA_SHEETS)
            .brandModelPlumbingFixtures(DEFAULT_BRAND_MODEL_PLUMBING_FIXTURES)
            .otherRelevantDevicesCertification(DEFAULT_OTHER_RELEVANT_DEVICES_CERTIFICATION)
            .heatRecoveryGrayWaterSolarOther(DEFAULT_HEAT_RECOVERY_GRAY_WATER_SOLAR_OTHER);
        return service;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Service createUpdatedEntity(EntityManager em) {
        Service service = new Service()
            .nameService(UPDATED_NAME_SERVICE)
            .companyName(UPDATED_COMPANY_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .plansAllFloorsVentilationPlans(UPDATED_PLANS_ALL_FLOORS_VENTILATION_PLANS)
            .energySimulationReport(UPDATED_ENERGY_SIMULATION_REPORT)
            .windowsTechnicalSheetAndUFactor(UPDATED_WINDOWS_TECHNICAL_SHEET_AND_U_FACTOR)
            .completeWallSection(UPDATED_COMPLETE_WALL_SECTION)
            .brandModelVentilationDevices(UPDATED_BRAND_MODEL_VENTILATION_DEVICES)
            .brandModelVeaters(UPDATED_BRAND_MODEL_VEATERS)
            .brandModelHotWaterTanks(UPDATED_BRAND_MODEL_HOT_WATER_TANKS)
            .brandModelHeatPumpAirConditioningUnits(UPDATED_BRAND_MODEL_HEAT_PUMP_AIR_CONDITIONING_UNITS)
            .typeLighting(UPDATED_TYPE_LIGHTING)
            .quantityEachModelAndDLCProductID(UPDATED_QUANTITY_EACH_MODEL_AND_DLC_PRODUCT_ID)
            .checksProvideTechnicalDataSheets(UPDATED_CHECKS_PROVIDE_TECHNICAL_DATA_SHEETS)
            .brandModelPlumbingFixtures(UPDATED_BRAND_MODEL_PLUMBING_FIXTURES)
            .otherRelevantDevicesCertification(UPDATED_OTHER_RELEVANT_DEVICES_CERTIFICATION)
            .heatRecoveryGrayWaterSolarOther(UPDATED_HEAT_RECOVERY_GRAY_WATER_SOLAR_OTHER);
        return service;
    }

    @BeforeEach
    public void initTest() {
        service = createEntity(em);
    }

    @Test
    @Transactional
    void createService() throws Exception {
        int databaseSizeBeforeCreate = serviceRepository.findAll().size();
        // Create the Service
        restServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(service)))
            .andExpect(status().isCreated());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeCreate + 1);
        Service testService = serviceList.get(serviceList.size() - 1);
        assertThat(testService.getNameService()).isEqualTo(DEFAULT_NAME_SERVICE);
        assertThat(testService.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testService.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testService.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testService.getPlansAllFloorsVentilationPlans()).isEqualTo(DEFAULT_PLANS_ALL_FLOORS_VENTILATION_PLANS);
        assertThat(testService.getEnergySimulationReport()).isEqualTo(DEFAULT_ENERGY_SIMULATION_REPORT);
        assertThat(testService.getWindowsTechnicalSheetAndUFactor()).isEqualTo(DEFAULT_WINDOWS_TECHNICAL_SHEET_AND_U_FACTOR);
        assertThat(testService.getCompleteWallSection()).isEqualTo(DEFAULT_COMPLETE_WALL_SECTION);
        assertThat(testService.getBrandModelVentilationDevices()).isEqualTo(DEFAULT_BRAND_MODEL_VENTILATION_DEVICES);
        assertThat(testService.getBrandModelVeaters()).isEqualTo(DEFAULT_BRAND_MODEL_VEATERS);
        assertThat(testService.getBrandModelHotWaterTanks()).isEqualTo(DEFAULT_BRAND_MODEL_HOT_WATER_TANKS);
        assertThat(testService.getBrandModelHeatPumpAirConditioningUnits()).isEqualTo(DEFAULT_BRAND_MODEL_HEAT_PUMP_AIR_CONDITIONING_UNITS);
        assertThat(testService.getTypeLighting()).isEqualTo(DEFAULT_TYPE_LIGHTING);
        assertThat(testService.getQuantityEachModelAndDLCProductID()).isEqualTo(DEFAULT_QUANTITY_EACH_MODEL_AND_DLC_PRODUCT_ID);
        assertThat(testService.getChecksProvideTechnicalDataSheets()).isEqualTo(DEFAULT_CHECKS_PROVIDE_TECHNICAL_DATA_SHEETS);
        assertThat(testService.getBrandModelPlumbingFixtures()).isEqualTo(DEFAULT_BRAND_MODEL_PLUMBING_FIXTURES);
        assertThat(testService.getOtherRelevantDevicesCertification()).isEqualTo(DEFAULT_OTHER_RELEVANT_DEVICES_CERTIFICATION);
        assertThat(testService.getHeatRecoveryGrayWaterSolarOther()).isEqualTo(DEFAULT_HEAT_RECOVERY_GRAY_WATER_SOLAR_OTHER);
    }

    @Test
    @Transactional
    void createServiceWithExistingId() throws Exception {
        // Create the Service with an existing ID
        service.setId(1L);

        int databaseSizeBeforeCreate = serviceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(service)))
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServices() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList
        restServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(service.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameService").value(hasItem(DEFAULT_NAME_SERVICE)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(
                jsonPath("$.[*].plansAllFloorsVentilationPlans").value(hasItem(DEFAULT_PLANS_ALL_FLOORS_VENTILATION_PLANS.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].energySimulationReport").value(hasItem(DEFAULT_ENERGY_SIMULATION_REPORT.booleanValue())))
            .andExpect(
                jsonPath("$.[*].windowsTechnicalSheetAndUFactor")
                    .value(hasItem(DEFAULT_WINDOWS_TECHNICAL_SHEET_AND_U_FACTOR.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].completeWallSection").value(hasItem(DEFAULT_COMPLETE_WALL_SECTION.booleanValue())))
            .andExpect(
                jsonPath("$.[*].brandModelVentilationDevices").value(hasItem(DEFAULT_BRAND_MODEL_VENTILATION_DEVICES.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].brandModelVeaters").value(hasItem(DEFAULT_BRAND_MODEL_VEATERS.booleanValue())))
            .andExpect(jsonPath("$.[*].brandModelHotWaterTanks").value(hasItem(DEFAULT_BRAND_MODEL_HOT_WATER_TANKS.booleanValue())))
            .andExpect(
                jsonPath("$.[*].brandModelHeatPumpAirConditioningUnits")
                    .value(hasItem(DEFAULT_BRAND_MODEL_HEAT_PUMP_AIR_CONDITIONING_UNITS.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].typeLighting").value(hasItem(DEFAULT_TYPE_LIGHTING.booleanValue())))
            .andExpect(
                jsonPath("$.[*].quantityEachModelAndDLCProductID")
                    .value(hasItem(DEFAULT_QUANTITY_EACH_MODEL_AND_DLC_PRODUCT_ID.booleanValue()))
            )
            .andExpect(
                jsonPath("$.[*].checksProvideTechnicalDataSheets")
                    .value(hasItem(DEFAULT_CHECKS_PROVIDE_TECHNICAL_DATA_SHEETS.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].brandModelPlumbingFixtures").value(hasItem(DEFAULT_BRAND_MODEL_PLUMBING_FIXTURES.booleanValue())))
            .andExpect(
                jsonPath("$.[*].otherRelevantDevicesCertification")
                    .value(hasItem(DEFAULT_OTHER_RELEVANT_DEVICES_CERTIFICATION.booleanValue()))
            )
            .andExpect(
                jsonPath("$.[*].heatRecoveryGrayWaterSolarOther")
                    .value(hasItem(DEFAULT_HEAT_RECOVERY_GRAY_WATER_SOLAR_OTHER.booleanValue()))
            );
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicesWithEagerRelationshipsIsEnabled() throws Exception {
        when(serviceRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServiceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(serviceRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(serviceRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServiceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(serviceRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getService() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get the service
        restServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, service.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(service.getId().intValue()))
            .andExpect(jsonPath("$.nameService").value(DEFAULT_NAME_SERVICE))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.plansAllFloorsVentilationPlans").value(DEFAULT_PLANS_ALL_FLOORS_VENTILATION_PLANS.booleanValue()))
            .andExpect(jsonPath("$.energySimulationReport").value(DEFAULT_ENERGY_SIMULATION_REPORT.booleanValue()))
            .andExpect(jsonPath("$.windowsTechnicalSheetAndUFactor").value(DEFAULT_WINDOWS_TECHNICAL_SHEET_AND_U_FACTOR.booleanValue()))
            .andExpect(jsonPath("$.completeWallSection").value(DEFAULT_COMPLETE_WALL_SECTION.booleanValue()))
            .andExpect(jsonPath("$.brandModelVentilationDevices").value(DEFAULT_BRAND_MODEL_VENTILATION_DEVICES.booleanValue()))
            .andExpect(jsonPath("$.brandModelVeaters").value(DEFAULT_BRAND_MODEL_VEATERS.booleanValue()))
            .andExpect(jsonPath("$.brandModelHotWaterTanks").value(DEFAULT_BRAND_MODEL_HOT_WATER_TANKS.booleanValue()))
            .andExpect(
                jsonPath("$.brandModelHeatPumpAirConditioningUnits")
                    .value(DEFAULT_BRAND_MODEL_HEAT_PUMP_AIR_CONDITIONING_UNITS.booleanValue())
            )
            .andExpect(jsonPath("$.typeLighting").value(DEFAULT_TYPE_LIGHTING.booleanValue()))
            .andExpect(jsonPath("$.quantityEachModelAndDLCProductID").value(DEFAULT_QUANTITY_EACH_MODEL_AND_DLC_PRODUCT_ID.booleanValue()))
            .andExpect(jsonPath("$.checksProvideTechnicalDataSheets").value(DEFAULT_CHECKS_PROVIDE_TECHNICAL_DATA_SHEETS.booleanValue()))
            .andExpect(jsonPath("$.brandModelPlumbingFixtures").value(DEFAULT_BRAND_MODEL_PLUMBING_FIXTURES.booleanValue()))
            .andExpect(jsonPath("$.otherRelevantDevicesCertification").value(DEFAULT_OTHER_RELEVANT_DEVICES_CERTIFICATION.booleanValue()))
            .andExpect(jsonPath("$.heatRecoveryGrayWaterSolarOther").value(DEFAULT_HEAT_RECOVERY_GRAY_WATER_SOLAR_OTHER.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingService() throws Exception {
        // Get the service
        restServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingService() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();

        // Update the service
        Service updatedService = serviceRepository.findById(service.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedService are not directly saved in db
        em.detach(updatedService);
        updatedService
            .nameService(UPDATED_NAME_SERVICE)
            .companyName(UPDATED_COMPANY_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .plansAllFloorsVentilationPlans(UPDATED_PLANS_ALL_FLOORS_VENTILATION_PLANS)
            .energySimulationReport(UPDATED_ENERGY_SIMULATION_REPORT)
            .windowsTechnicalSheetAndUFactor(UPDATED_WINDOWS_TECHNICAL_SHEET_AND_U_FACTOR)
            .completeWallSection(UPDATED_COMPLETE_WALL_SECTION)
            .brandModelVentilationDevices(UPDATED_BRAND_MODEL_VENTILATION_DEVICES)
            .brandModelVeaters(UPDATED_BRAND_MODEL_VEATERS)
            .brandModelHotWaterTanks(UPDATED_BRAND_MODEL_HOT_WATER_TANKS)
            .brandModelHeatPumpAirConditioningUnits(UPDATED_BRAND_MODEL_HEAT_PUMP_AIR_CONDITIONING_UNITS)
            .typeLighting(UPDATED_TYPE_LIGHTING)
            .quantityEachModelAndDLCProductID(UPDATED_QUANTITY_EACH_MODEL_AND_DLC_PRODUCT_ID)
            .checksProvideTechnicalDataSheets(UPDATED_CHECKS_PROVIDE_TECHNICAL_DATA_SHEETS)
            .brandModelPlumbingFixtures(UPDATED_BRAND_MODEL_PLUMBING_FIXTURES)
            .otherRelevantDevicesCertification(UPDATED_OTHER_RELEVANT_DEVICES_CERTIFICATION)
            .heatRecoveryGrayWaterSolarOther(UPDATED_HEAT_RECOVERY_GRAY_WATER_SOLAR_OTHER);

        restServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedService))
            )
            .andExpect(status().isOk());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);
        Service testService = serviceList.get(serviceList.size() - 1);
        assertThat(testService.getNameService()).isEqualTo(UPDATED_NAME_SERVICE);
        assertThat(testService.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testService.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testService.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testService.getPlansAllFloorsVentilationPlans()).isEqualTo(UPDATED_PLANS_ALL_FLOORS_VENTILATION_PLANS);
        assertThat(testService.getEnergySimulationReport()).isEqualTo(UPDATED_ENERGY_SIMULATION_REPORT);
        assertThat(testService.getWindowsTechnicalSheetAndUFactor()).isEqualTo(UPDATED_WINDOWS_TECHNICAL_SHEET_AND_U_FACTOR);
        assertThat(testService.getCompleteWallSection()).isEqualTo(UPDATED_COMPLETE_WALL_SECTION);
        assertThat(testService.getBrandModelVentilationDevices()).isEqualTo(UPDATED_BRAND_MODEL_VENTILATION_DEVICES);
        assertThat(testService.getBrandModelVeaters()).isEqualTo(UPDATED_BRAND_MODEL_VEATERS);
        assertThat(testService.getBrandModelHotWaterTanks()).isEqualTo(UPDATED_BRAND_MODEL_HOT_WATER_TANKS);
        assertThat(testService.getBrandModelHeatPumpAirConditioningUnits()).isEqualTo(UPDATED_BRAND_MODEL_HEAT_PUMP_AIR_CONDITIONING_UNITS);
        assertThat(testService.getTypeLighting()).isEqualTo(UPDATED_TYPE_LIGHTING);
        assertThat(testService.getQuantityEachModelAndDLCProductID()).isEqualTo(UPDATED_QUANTITY_EACH_MODEL_AND_DLC_PRODUCT_ID);
        assertThat(testService.getChecksProvideTechnicalDataSheets()).isEqualTo(UPDATED_CHECKS_PROVIDE_TECHNICAL_DATA_SHEETS);
        assertThat(testService.getBrandModelPlumbingFixtures()).isEqualTo(UPDATED_BRAND_MODEL_PLUMBING_FIXTURES);
        assertThat(testService.getOtherRelevantDevicesCertification()).isEqualTo(UPDATED_OTHER_RELEVANT_DEVICES_CERTIFICATION);
        assertThat(testService.getHeatRecoveryGrayWaterSolarOther()).isEqualTo(UPDATED_HEAT_RECOVERY_GRAY_WATER_SOLAR_OTHER);
    }

    @Test
    @Transactional
    void putNonExistingService() throws Exception {
        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();
        service.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, service.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(service))
            )
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchService() throws Exception {
        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();
        service.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(service))
            )
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamService() throws Exception {
        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();
        service.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(service)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceWithPatch() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();

        // Update the service using partial update
        Service partialUpdatedService = new Service();
        partialUpdatedService.setId(service.getId());

        partialUpdatedService
            .nameService(UPDATED_NAME_SERVICE)
            .companyName(UPDATED_COMPANY_NAME)
            .email(UPDATED_EMAIL)
            .plansAllFloorsVentilationPlans(UPDATED_PLANS_ALL_FLOORS_VENTILATION_PLANS)
            .energySimulationReport(UPDATED_ENERGY_SIMULATION_REPORT)
            .windowsTechnicalSheetAndUFactor(UPDATED_WINDOWS_TECHNICAL_SHEET_AND_U_FACTOR)
            .brandModelHotWaterTanks(UPDATED_BRAND_MODEL_HOT_WATER_TANKS)
            .typeLighting(UPDATED_TYPE_LIGHTING)
            .checksProvideTechnicalDataSheets(UPDATED_CHECKS_PROVIDE_TECHNICAL_DATA_SHEETS)
            .otherRelevantDevicesCertification(UPDATED_OTHER_RELEVANT_DEVICES_CERTIFICATION);

        restServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedService))
            )
            .andExpect(status().isOk());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);
        Service testService = serviceList.get(serviceList.size() - 1);
        assertThat(testService.getNameService()).isEqualTo(UPDATED_NAME_SERVICE);
        assertThat(testService.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testService.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testService.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testService.getPlansAllFloorsVentilationPlans()).isEqualTo(UPDATED_PLANS_ALL_FLOORS_VENTILATION_PLANS);
        assertThat(testService.getEnergySimulationReport()).isEqualTo(UPDATED_ENERGY_SIMULATION_REPORT);
        assertThat(testService.getWindowsTechnicalSheetAndUFactor()).isEqualTo(UPDATED_WINDOWS_TECHNICAL_SHEET_AND_U_FACTOR);
        assertThat(testService.getCompleteWallSection()).isEqualTo(DEFAULT_COMPLETE_WALL_SECTION);
        assertThat(testService.getBrandModelVentilationDevices()).isEqualTo(DEFAULT_BRAND_MODEL_VENTILATION_DEVICES);
        assertThat(testService.getBrandModelVeaters()).isEqualTo(DEFAULT_BRAND_MODEL_VEATERS);
        assertThat(testService.getBrandModelHotWaterTanks()).isEqualTo(UPDATED_BRAND_MODEL_HOT_WATER_TANKS);
        assertThat(testService.getBrandModelHeatPumpAirConditioningUnits()).isEqualTo(DEFAULT_BRAND_MODEL_HEAT_PUMP_AIR_CONDITIONING_UNITS);
        assertThat(testService.getTypeLighting()).isEqualTo(UPDATED_TYPE_LIGHTING);
        assertThat(testService.getQuantityEachModelAndDLCProductID()).isEqualTo(DEFAULT_QUANTITY_EACH_MODEL_AND_DLC_PRODUCT_ID);
        assertThat(testService.getChecksProvideTechnicalDataSheets()).isEqualTo(UPDATED_CHECKS_PROVIDE_TECHNICAL_DATA_SHEETS);
        assertThat(testService.getBrandModelPlumbingFixtures()).isEqualTo(DEFAULT_BRAND_MODEL_PLUMBING_FIXTURES);
        assertThat(testService.getOtherRelevantDevicesCertification()).isEqualTo(UPDATED_OTHER_RELEVANT_DEVICES_CERTIFICATION);
        assertThat(testService.getHeatRecoveryGrayWaterSolarOther()).isEqualTo(DEFAULT_HEAT_RECOVERY_GRAY_WATER_SOLAR_OTHER);
    }

    @Test
    @Transactional
    void fullUpdateServiceWithPatch() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();

        // Update the service using partial update
        Service partialUpdatedService = new Service();
        partialUpdatedService.setId(service.getId());

        partialUpdatedService
            .nameService(UPDATED_NAME_SERVICE)
            .companyName(UPDATED_COMPANY_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .plansAllFloorsVentilationPlans(UPDATED_PLANS_ALL_FLOORS_VENTILATION_PLANS)
            .energySimulationReport(UPDATED_ENERGY_SIMULATION_REPORT)
            .windowsTechnicalSheetAndUFactor(UPDATED_WINDOWS_TECHNICAL_SHEET_AND_U_FACTOR)
            .completeWallSection(UPDATED_COMPLETE_WALL_SECTION)
            .brandModelVentilationDevices(UPDATED_BRAND_MODEL_VENTILATION_DEVICES)
            .brandModelVeaters(UPDATED_BRAND_MODEL_VEATERS)
            .brandModelHotWaterTanks(UPDATED_BRAND_MODEL_HOT_WATER_TANKS)
            .brandModelHeatPumpAirConditioningUnits(UPDATED_BRAND_MODEL_HEAT_PUMP_AIR_CONDITIONING_UNITS)
            .typeLighting(UPDATED_TYPE_LIGHTING)
            .quantityEachModelAndDLCProductID(UPDATED_QUANTITY_EACH_MODEL_AND_DLC_PRODUCT_ID)
            .checksProvideTechnicalDataSheets(UPDATED_CHECKS_PROVIDE_TECHNICAL_DATA_SHEETS)
            .brandModelPlumbingFixtures(UPDATED_BRAND_MODEL_PLUMBING_FIXTURES)
            .otherRelevantDevicesCertification(UPDATED_OTHER_RELEVANT_DEVICES_CERTIFICATION)
            .heatRecoveryGrayWaterSolarOther(UPDATED_HEAT_RECOVERY_GRAY_WATER_SOLAR_OTHER);

        restServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedService))
            )
            .andExpect(status().isOk());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);
        Service testService = serviceList.get(serviceList.size() - 1);
        assertThat(testService.getNameService()).isEqualTo(UPDATED_NAME_SERVICE);
        assertThat(testService.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testService.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testService.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testService.getPlansAllFloorsVentilationPlans()).isEqualTo(UPDATED_PLANS_ALL_FLOORS_VENTILATION_PLANS);
        assertThat(testService.getEnergySimulationReport()).isEqualTo(UPDATED_ENERGY_SIMULATION_REPORT);
        assertThat(testService.getWindowsTechnicalSheetAndUFactor()).isEqualTo(UPDATED_WINDOWS_TECHNICAL_SHEET_AND_U_FACTOR);
        assertThat(testService.getCompleteWallSection()).isEqualTo(UPDATED_COMPLETE_WALL_SECTION);
        assertThat(testService.getBrandModelVentilationDevices()).isEqualTo(UPDATED_BRAND_MODEL_VENTILATION_DEVICES);
        assertThat(testService.getBrandModelVeaters()).isEqualTo(UPDATED_BRAND_MODEL_VEATERS);
        assertThat(testService.getBrandModelHotWaterTanks()).isEqualTo(UPDATED_BRAND_MODEL_HOT_WATER_TANKS);
        assertThat(testService.getBrandModelHeatPumpAirConditioningUnits()).isEqualTo(UPDATED_BRAND_MODEL_HEAT_PUMP_AIR_CONDITIONING_UNITS);
        assertThat(testService.getTypeLighting()).isEqualTo(UPDATED_TYPE_LIGHTING);
        assertThat(testService.getQuantityEachModelAndDLCProductID()).isEqualTo(UPDATED_QUANTITY_EACH_MODEL_AND_DLC_PRODUCT_ID);
        assertThat(testService.getChecksProvideTechnicalDataSheets()).isEqualTo(UPDATED_CHECKS_PROVIDE_TECHNICAL_DATA_SHEETS);
        assertThat(testService.getBrandModelPlumbingFixtures()).isEqualTo(UPDATED_BRAND_MODEL_PLUMBING_FIXTURES);
        assertThat(testService.getOtherRelevantDevicesCertification()).isEqualTo(UPDATED_OTHER_RELEVANT_DEVICES_CERTIFICATION);
        assertThat(testService.getHeatRecoveryGrayWaterSolarOther()).isEqualTo(UPDATED_HEAT_RECOVERY_GRAY_WATER_SOLAR_OTHER);
    }

    @Test
    @Transactional
    void patchNonExistingService() throws Exception {
        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();
        service.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, service.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(service))
            )
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchService() throws Exception {
        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();
        service.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(service))
            )
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamService() throws Exception {
        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();
        service.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(service)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteService() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        int databaseSizeBeforeDelete = serviceRepository.findAll().size();

        // Delete the service
        restServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, service.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
