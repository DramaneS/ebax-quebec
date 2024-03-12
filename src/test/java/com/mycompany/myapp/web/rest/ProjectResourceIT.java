package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Project;
import com.mycompany.myapp.repository.ProjectRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectResourceIT {

    private static final String DEFAULT_NAME_BUILDING = "AAAAAAAAAA";
    private static final String UPDATED_NAME_BUILDING = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_ADDRESS_BUILDING = "AAAAAAAAAA";
    private static final String UPDATED_FULL_ADDRESS_BUILDING = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER_HOUSING_UNITS = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_HOUSING_UNITS = "BBBBBBBBBB";

    private static final String DEFAULT_ELECTRICITY_SUPPLIER = "AAAAAAAAAA";
    private static final String UPDATED_ELECTRICITY_SUPPLIER = "BBBBBBBBBB";

    private static final String DEFAULT_HYDRO_QUEBEC_CONTRACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HYDRO_QUEBEC_CONTRACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_HYDRO_QUEBEC_METER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HYDRO_QUEBEC_METER_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IF_SEVERAL_METERS = false;
    private static final Boolean UPDATED_IF_SEVERAL_METERS = true;

    private static final String DEFAULT_SPECIFY_METE_INTENDED = "AAAAAAAAAA";
    private static final String UPDATED_SPECIFY_METE_INTENDED = "BBBBBBBBBB";

    private static final String DEFAULT_HYDRO_QUEBEC_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HYDRO_QUEBEC_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_BUILLDING = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_BUILLDING = "BBBBBBBBBB";

    private static final String DEFAULT_NATURE_PROJECT = "AAAAAAAAAA";
    private static final String UPDATED_NATURE_PROJECT = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE_WORK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE_WORK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE_WORK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE_WORK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectMockMvc;

    private Project project;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createEntity(EntityManager em) {
        Project project = new Project()
            .nameBuilding(DEFAULT_NAME_BUILDING)
            .fullAddressBuilding(DEFAULT_FULL_ADDRESS_BUILDING)
            .numberHousingUnits(DEFAULT_NUMBER_HOUSING_UNITS)
            .electricitySupplier(DEFAULT_ELECTRICITY_SUPPLIER)
            .hydroQuebecContractNumber(DEFAULT_HYDRO_QUEBEC_CONTRACT_NUMBER)
            .hydroQuebecMeterNumber(DEFAULT_HYDRO_QUEBEC_METER_NUMBER)
            .ifSeveralMeters(DEFAULT_IF_SEVERAL_METERS)
            .specifyMeteIntended(DEFAULT_SPECIFY_METE_INTENDED)
            .hydroQuebecAccountNumber(DEFAULT_HYDRO_QUEBEC_ACCOUNT_NUMBER)
            .fileNumber(DEFAULT_FILE_NUMBER)
            .typeBuillding(DEFAULT_TYPE_BUILLDING)
            .natureProject(DEFAULT_NATURE_PROJECT)
            .startDateWork(DEFAULT_START_DATE_WORK)
            .endDateWork(DEFAULT_END_DATE_WORK);
        return project;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createUpdatedEntity(EntityManager em) {
        Project project = new Project()
            .nameBuilding(UPDATED_NAME_BUILDING)
            .fullAddressBuilding(UPDATED_FULL_ADDRESS_BUILDING)
            .numberHousingUnits(UPDATED_NUMBER_HOUSING_UNITS)
            .electricitySupplier(UPDATED_ELECTRICITY_SUPPLIER)
            .hydroQuebecContractNumber(UPDATED_HYDRO_QUEBEC_CONTRACT_NUMBER)
            .hydroQuebecMeterNumber(UPDATED_HYDRO_QUEBEC_METER_NUMBER)
            .ifSeveralMeters(UPDATED_IF_SEVERAL_METERS)
            .specifyMeteIntended(UPDATED_SPECIFY_METE_INTENDED)
            .hydroQuebecAccountNumber(UPDATED_HYDRO_QUEBEC_ACCOUNT_NUMBER)
            .fileNumber(UPDATED_FILE_NUMBER)
            .typeBuillding(UPDATED_TYPE_BUILLDING)
            .natureProject(UPDATED_NATURE_PROJECT)
            .startDateWork(UPDATED_START_DATE_WORK)
            .endDateWork(UPDATED_END_DATE_WORK);
        return project;
    }

    @BeforeEach
    public void initTest() {
        project = createEntity(em);
    }

    @Test
    @Transactional
    void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();
        // Create the Project
        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getNameBuilding()).isEqualTo(DEFAULT_NAME_BUILDING);
        assertThat(testProject.getFullAddressBuilding()).isEqualTo(DEFAULT_FULL_ADDRESS_BUILDING);
        assertThat(testProject.getNumberHousingUnits()).isEqualTo(DEFAULT_NUMBER_HOUSING_UNITS);
        assertThat(testProject.getElectricitySupplier()).isEqualTo(DEFAULT_ELECTRICITY_SUPPLIER);
        assertThat(testProject.getHydroQuebecContractNumber()).isEqualTo(DEFAULT_HYDRO_QUEBEC_CONTRACT_NUMBER);
        assertThat(testProject.getHydroQuebecMeterNumber()).isEqualTo(DEFAULT_HYDRO_QUEBEC_METER_NUMBER);
        assertThat(testProject.getIfSeveralMeters()).isEqualTo(DEFAULT_IF_SEVERAL_METERS);
        assertThat(testProject.getSpecifyMeteIntended()).isEqualTo(DEFAULT_SPECIFY_METE_INTENDED);
        assertThat(testProject.getHydroQuebecAccountNumber()).isEqualTo(DEFAULT_HYDRO_QUEBEC_ACCOUNT_NUMBER);
        assertThat(testProject.getFileNumber()).isEqualTo(DEFAULT_FILE_NUMBER);
        assertThat(testProject.getTypeBuillding()).isEqualTo(DEFAULT_TYPE_BUILLDING);
        assertThat(testProject.getNatureProject()).isEqualTo(DEFAULT_NATURE_PROJECT);
        assertThat(testProject.getStartDateWork()).isEqualTo(DEFAULT_START_DATE_WORK);
        assertThat(testProject.getEndDateWork()).isEqualTo(DEFAULT_END_DATE_WORK);
    }

    @Test
    @Transactional
    void createProjectWithExistingId() throws Exception {
        // Create the Project with an existing ID
        project.setId(1L);

        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList
        restProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameBuilding").value(hasItem(DEFAULT_NAME_BUILDING)))
            .andExpect(jsonPath("$.[*].fullAddressBuilding").value(hasItem(DEFAULT_FULL_ADDRESS_BUILDING)))
            .andExpect(jsonPath("$.[*].numberHousingUnits").value(hasItem(DEFAULT_NUMBER_HOUSING_UNITS)))
            .andExpect(jsonPath("$.[*].electricitySupplier").value(hasItem(DEFAULT_ELECTRICITY_SUPPLIER)))
            .andExpect(jsonPath("$.[*].hydroQuebecContractNumber").value(hasItem(DEFAULT_HYDRO_QUEBEC_CONTRACT_NUMBER)))
            .andExpect(jsonPath("$.[*].hydroQuebecMeterNumber").value(hasItem(DEFAULT_HYDRO_QUEBEC_METER_NUMBER)))
            .andExpect(jsonPath("$.[*].ifSeveralMeters").value(hasItem(DEFAULT_IF_SEVERAL_METERS.booleanValue())))
            .andExpect(jsonPath("$.[*].specifyMeteIntended").value(hasItem(DEFAULT_SPECIFY_METE_INTENDED)))
            .andExpect(jsonPath("$.[*].hydroQuebecAccountNumber").value(hasItem(DEFAULT_HYDRO_QUEBEC_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].fileNumber").value(hasItem(DEFAULT_FILE_NUMBER)))
            .andExpect(jsonPath("$.[*].typeBuillding").value(hasItem(DEFAULT_TYPE_BUILLDING)))
            .andExpect(jsonPath("$.[*].natureProject").value(hasItem(DEFAULT_NATURE_PROJECT)))
            .andExpect(jsonPath("$.[*].startDateWork").value(hasItem(DEFAULT_START_DATE_WORK.toString())))
            .andExpect(jsonPath("$.[*].endDateWork").value(hasItem(DEFAULT_END_DATE_WORK.toString())));
    }

    @Test
    @Transactional
    void getProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get the project
        restProjectMockMvc
            .perform(get(ENTITY_API_URL_ID, project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(project.getId().intValue()))
            .andExpect(jsonPath("$.nameBuilding").value(DEFAULT_NAME_BUILDING))
            .andExpect(jsonPath("$.fullAddressBuilding").value(DEFAULT_FULL_ADDRESS_BUILDING))
            .andExpect(jsonPath("$.numberHousingUnits").value(DEFAULT_NUMBER_HOUSING_UNITS))
            .andExpect(jsonPath("$.electricitySupplier").value(DEFAULT_ELECTRICITY_SUPPLIER))
            .andExpect(jsonPath("$.hydroQuebecContractNumber").value(DEFAULT_HYDRO_QUEBEC_CONTRACT_NUMBER))
            .andExpect(jsonPath("$.hydroQuebecMeterNumber").value(DEFAULT_HYDRO_QUEBEC_METER_NUMBER))
            .andExpect(jsonPath("$.ifSeveralMeters").value(DEFAULT_IF_SEVERAL_METERS.booleanValue()))
            .andExpect(jsonPath("$.specifyMeteIntended").value(DEFAULT_SPECIFY_METE_INTENDED))
            .andExpect(jsonPath("$.hydroQuebecAccountNumber").value(DEFAULT_HYDRO_QUEBEC_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.fileNumber").value(DEFAULT_FILE_NUMBER))
            .andExpect(jsonPath("$.typeBuillding").value(DEFAULT_TYPE_BUILLDING))
            .andExpect(jsonPath("$.natureProject").value(DEFAULT_NATURE_PROJECT))
            .andExpect(jsonPath("$.startDateWork").value(DEFAULT_START_DATE_WORK.toString()))
            .andExpect(jsonPath("$.endDateWork").value(DEFAULT_END_DATE_WORK.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        Project updatedProject = projectRepository.findById(project.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProject are not directly saved in db
        em.detach(updatedProject);
        updatedProject
            .nameBuilding(UPDATED_NAME_BUILDING)
            .fullAddressBuilding(UPDATED_FULL_ADDRESS_BUILDING)
            .numberHousingUnits(UPDATED_NUMBER_HOUSING_UNITS)
            .electricitySupplier(UPDATED_ELECTRICITY_SUPPLIER)
            .hydroQuebecContractNumber(UPDATED_HYDRO_QUEBEC_CONTRACT_NUMBER)
            .hydroQuebecMeterNumber(UPDATED_HYDRO_QUEBEC_METER_NUMBER)
            .ifSeveralMeters(UPDATED_IF_SEVERAL_METERS)
            .specifyMeteIntended(UPDATED_SPECIFY_METE_INTENDED)
            .hydroQuebecAccountNumber(UPDATED_HYDRO_QUEBEC_ACCOUNT_NUMBER)
            .fileNumber(UPDATED_FILE_NUMBER)
            .typeBuillding(UPDATED_TYPE_BUILLDING)
            .natureProject(UPDATED_NATURE_PROJECT)
            .startDateWork(UPDATED_START_DATE_WORK)
            .endDateWork(UPDATED_END_DATE_WORK);

        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProject))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getNameBuilding()).isEqualTo(UPDATED_NAME_BUILDING);
        assertThat(testProject.getFullAddressBuilding()).isEqualTo(UPDATED_FULL_ADDRESS_BUILDING);
        assertThat(testProject.getNumberHousingUnits()).isEqualTo(UPDATED_NUMBER_HOUSING_UNITS);
        assertThat(testProject.getElectricitySupplier()).isEqualTo(UPDATED_ELECTRICITY_SUPPLIER);
        assertThat(testProject.getHydroQuebecContractNumber()).isEqualTo(UPDATED_HYDRO_QUEBEC_CONTRACT_NUMBER);
        assertThat(testProject.getHydroQuebecMeterNumber()).isEqualTo(UPDATED_HYDRO_QUEBEC_METER_NUMBER);
        assertThat(testProject.getIfSeveralMeters()).isEqualTo(UPDATED_IF_SEVERAL_METERS);
        assertThat(testProject.getSpecifyMeteIntended()).isEqualTo(UPDATED_SPECIFY_METE_INTENDED);
        assertThat(testProject.getHydroQuebecAccountNumber()).isEqualTo(UPDATED_HYDRO_QUEBEC_ACCOUNT_NUMBER);
        assertThat(testProject.getFileNumber()).isEqualTo(UPDATED_FILE_NUMBER);
        assertThat(testProject.getTypeBuillding()).isEqualTo(UPDATED_TYPE_BUILLDING);
        assertThat(testProject.getNatureProject()).isEqualTo(UPDATED_NATURE_PROJECT);
        assertThat(testProject.getStartDateWork()).isEqualTo(UPDATED_START_DATE_WORK);
        assertThat(testProject.getEndDateWork()).isEqualTo(UPDATED_END_DATE_WORK);
    }

    @Test
    @Transactional
    void putNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, project.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(project))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(project))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectWithPatch() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project using partial update
        Project partialUpdatedProject = new Project();
        partialUpdatedProject.setId(project.getId());

        partialUpdatedProject
            .nameBuilding(UPDATED_NAME_BUILDING)
            .fullAddressBuilding(UPDATED_FULL_ADDRESS_BUILDING)
            .numberHousingUnits(UPDATED_NUMBER_HOUSING_UNITS)
            .electricitySupplier(UPDATED_ELECTRICITY_SUPPLIER)
            .hydroQuebecContractNumber(UPDATED_HYDRO_QUEBEC_CONTRACT_NUMBER)
            .ifSeveralMeters(UPDATED_IF_SEVERAL_METERS)
            .typeBuillding(UPDATED_TYPE_BUILLDING)
            .startDateWork(UPDATED_START_DATE_WORK)
            .endDateWork(UPDATED_END_DATE_WORK);

        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProject))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getNameBuilding()).isEqualTo(UPDATED_NAME_BUILDING);
        assertThat(testProject.getFullAddressBuilding()).isEqualTo(UPDATED_FULL_ADDRESS_BUILDING);
        assertThat(testProject.getNumberHousingUnits()).isEqualTo(UPDATED_NUMBER_HOUSING_UNITS);
        assertThat(testProject.getElectricitySupplier()).isEqualTo(UPDATED_ELECTRICITY_SUPPLIER);
        assertThat(testProject.getHydroQuebecContractNumber()).isEqualTo(UPDATED_HYDRO_QUEBEC_CONTRACT_NUMBER);
        assertThat(testProject.getHydroQuebecMeterNumber()).isEqualTo(DEFAULT_HYDRO_QUEBEC_METER_NUMBER);
        assertThat(testProject.getIfSeveralMeters()).isEqualTo(UPDATED_IF_SEVERAL_METERS);
        assertThat(testProject.getSpecifyMeteIntended()).isEqualTo(DEFAULT_SPECIFY_METE_INTENDED);
        assertThat(testProject.getHydroQuebecAccountNumber()).isEqualTo(DEFAULT_HYDRO_QUEBEC_ACCOUNT_NUMBER);
        assertThat(testProject.getFileNumber()).isEqualTo(DEFAULT_FILE_NUMBER);
        assertThat(testProject.getTypeBuillding()).isEqualTo(UPDATED_TYPE_BUILLDING);
        assertThat(testProject.getNatureProject()).isEqualTo(DEFAULT_NATURE_PROJECT);
        assertThat(testProject.getStartDateWork()).isEqualTo(UPDATED_START_DATE_WORK);
        assertThat(testProject.getEndDateWork()).isEqualTo(UPDATED_END_DATE_WORK);
    }

    @Test
    @Transactional
    void fullUpdateProjectWithPatch() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project using partial update
        Project partialUpdatedProject = new Project();
        partialUpdatedProject.setId(project.getId());

        partialUpdatedProject
            .nameBuilding(UPDATED_NAME_BUILDING)
            .fullAddressBuilding(UPDATED_FULL_ADDRESS_BUILDING)
            .numberHousingUnits(UPDATED_NUMBER_HOUSING_UNITS)
            .electricitySupplier(UPDATED_ELECTRICITY_SUPPLIER)
            .hydroQuebecContractNumber(UPDATED_HYDRO_QUEBEC_CONTRACT_NUMBER)
            .hydroQuebecMeterNumber(UPDATED_HYDRO_QUEBEC_METER_NUMBER)
            .ifSeveralMeters(UPDATED_IF_SEVERAL_METERS)
            .specifyMeteIntended(UPDATED_SPECIFY_METE_INTENDED)
            .hydroQuebecAccountNumber(UPDATED_HYDRO_QUEBEC_ACCOUNT_NUMBER)
            .fileNumber(UPDATED_FILE_NUMBER)
            .typeBuillding(UPDATED_TYPE_BUILLDING)
            .natureProject(UPDATED_NATURE_PROJECT)
            .startDateWork(UPDATED_START_DATE_WORK)
            .endDateWork(UPDATED_END_DATE_WORK);

        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProject))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getNameBuilding()).isEqualTo(UPDATED_NAME_BUILDING);
        assertThat(testProject.getFullAddressBuilding()).isEqualTo(UPDATED_FULL_ADDRESS_BUILDING);
        assertThat(testProject.getNumberHousingUnits()).isEqualTo(UPDATED_NUMBER_HOUSING_UNITS);
        assertThat(testProject.getElectricitySupplier()).isEqualTo(UPDATED_ELECTRICITY_SUPPLIER);
        assertThat(testProject.getHydroQuebecContractNumber()).isEqualTo(UPDATED_HYDRO_QUEBEC_CONTRACT_NUMBER);
        assertThat(testProject.getHydroQuebecMeterNumber()).isEqualTo(UPDATED_HYDRO_QUEBEC_METER_NUMBER);
        assertThat(testProject.getIfSeveralMeters()).isEqualTo(UPDATED_IF_SEVERAL_METERS);
        assertThat(testProject.getSpecifyMeteIntended()).isEqualTo(UPDATED_SPECIFY_METE_INTENDED);
        assertThat(testProject.getHydroQuebecAccountNumber()).isEqualTo(UPDATED_HYDRO_QUEBEC_ACCOUNT_NUMBER);
        assertThat(testProject.getFileNumber()).isEqualTo(UPDATED_FILE_NUMBER);
        assertThat(testProject.getTypeBuillding()).isEqualTo(UPDATED_TYPE_BUILLDING);
        assertThat(testProject.getNatureProject()).isEqualTo(UPDATED_NATURE_PROJECT);
        assertThat(testProject.getStartDateWork()).isEqualTo(UPDATED_START_DATE_WORK);
        assertThat(testProject.getEndDateWork()).isEqualTo(UPDATED_END_DATE_WORK);
    }

    @Test
    @Transactional
    void patchNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, project.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(project))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(project))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Delete the project
        restProjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, project.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
