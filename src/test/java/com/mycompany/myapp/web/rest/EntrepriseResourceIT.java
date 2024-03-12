package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Entreprise;
import com.mycompany.myapp.repository.EntrepriseRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link EntrepriseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntrepriseResourceIT {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NEQ_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NEQ_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_THISTAX_EXEMPT_ORGANIZATION = false;
    private static final Boolean UPDATED_IS_THISTAX_EXEMPT_ORGANIZATION = true;

    private static final String DEFAULT_RESOURCE_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/entreprises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntrepriseMockMvc;

    private Entreprise entreprise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entreprise createEntity(EntityManager em) {
        Entreprise entreprise = new Entreprise()
            .companyName(DEFAULT_COMPANY_NAME)
            .companyNeqNumber(DEFAULT_COMPANY_NEQ_NUMBER)
            .isThistaxExemptOrganization(DEFAULT_IS_THISTAX_EXEMPT_ORGANIZATION)
            .resourcePerson(DEFAULT_RESOURCE_PERSON)
            .address(DEFAULT_ADDRESS)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE);
        return entreprise;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entreprise createUpdatedEntity(EntityManager em) {
        Entreprise entreprise = new Entreprise()
            .companyName(UPDATED_COMPANY_NAME)
            .companyNeqNumber(UPDATED_COMPANY_NEQ_NUMBER)
            .isThistaxExemptOrganization(UPDATED_IS_THISTAX_EXEMPT_ORGANIZATION)
            .resourcePerson(UPDATED_RESOURCE_PERSON)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
        return entreprise;
    }

    @BeforeEach
    public void initTest() {
        entreprise = createEntity(em);
    }

    @Test
    @Transactional
    void createEntreprise() throws Exception {
        int databaseSizeBeforeCreate = entrepriseRepository.findAll().size();
        // Create the Entreprise
        restEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entreprise)))
            .andExpect(status().isCreated());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeCreate + 1);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testEntreprise.getCompanyNeqNumber()).isEqualTo(DEFAULT_COMPANY_NEQ_NUMBER);
        assertThat(testEntreprise.getIsThistaxExemptOrganization()).isEqualTo(DEFAULT_IS_THISTAX_EXEMPT_ORGANIZATION);
        assertThat(testEntreprise.getResourcePerson()).isEqualTo(DEFAULT_RESOURCE_PERSON);
        assertThat(testEntreprise.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEntreprise.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEntreprise.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void createEntrepriseWithExistingId() throws Exception {
        // Create the Entreprise with an existing ID
        entreprise.setId(1L);

        int databaseSizeBeforeCreate = entrepriseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entreprise)))
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEntreprises() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        // Get all the entrepriseList
        restEntrepriseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entreprise.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].companyNeqNumber").value(hasItem(DEFAULT_COMPANY_NEQ_NUMBER)))
            .andExpect(jsonPath("$.[*].isThistaxExemptOrganization").value(hasItem(DEFAULT_IS_THISTAX_EXEMPT_ORGANIZATION.booleanValue())))
            .andExpect(jsonPath("$.[*].resourcePerson").value(hasItem(DEFAULT_RESOURCE_PERSON)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    @Transactional
    void getEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        // Get the entreprise
        restEntrepriseMockMvc
            .perform(get(ENTITY_API_URL_ID, entreprise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entreprise.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.companyNeqNumber").value(DEFAULT_COMPANY_NEQ_NUMBER))
            .andExpect(jsonPath("$.isThistaxExemptOrganization").value(DEFAULT_IS_THISTAX_EXEMPT_ORGANIZATION.booleanValue()))
            .andExpect(jsonPath("$.resourcePerson").value(DEFAULT_RESOURCE_PERSON))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    void getNonExistingEntreprise() throws Exception {
        // Get the entreprise
        restEntrepriseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise
        Entreprise updatedEntreprise = entrepriseRepository.findById(entreprise.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEntreprise are not directly saved in db
        em.detach(updatedEntreprise);
        updatedEntreprise
            .companyName(UPDATED_COMPANY_NAME)
            .companyNeqNumber(UPDATED_COMPANY_NEQ_NUMBER)
            .isThistaxExemptOrganization(UPDATED_IS_THISTAX_EXEMPT_ORGANIZATION)
            .resourcePerson(UPDATED_RESOURCE_PERSON)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);

        restEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEntreprise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEntreprise))
            )
            .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testEntreprise.getCompanyNeqNumber()).isEqualTo(UPDATED_COMPANY_NEQ_NUMBER);
        assertThat(testEntreprise.getIsThistaxExemptOrganization()).isEqualTo(UPDATED_IS_THISTAX_EXEMPT_ORGANIZATION);
        assertThat(testEntreprise.getResourcePerson()).isEqualTo(UPDATED_RESOURCE_PERSON);
        assertThat(testEntreprise.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEntreprise.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEntreprise.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void putNonExistingEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entreprise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entreprise)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntrepriseWithPatch() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise using partial update
        Entreprise partialUpdatedEntreprise = new Entreprise();
        partialUpdatedEntreprise.setId(entreprise.getId());

        partialUpdatedEntreprise.companyName(UPDATED_COMPANY_NAME).address(UPDATED_ADDRESS).email(UPDATED_EMAIL).phone(UPDATED_PHONE);

        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntreprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntreprise))
            )
            .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testEntreprise.getCompanyNeqNumber()).isEqualTo(DEFAULT_COMPANY_NEQ_NUMBER);
        assertThat(testEntreprise.getIsThistaxExemptOrganization()).isEqualTo(DEFAULT_IS_THISTAX_EXEMPT_ORGANIZATION);
        assertThat(testEntreprise.getResourcePerson()).isEqualTo(DEFAULT_RESOURCE_PERSON);
        assertThat(testEntreprise.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEntreprise.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEntreprise.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void fullUpdateEntrepriseWithPatch() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise using partial update
        Entreprise partialUpdatedEntreprise = new Entreprise();
        partialUpdatedEntreprise.setId(entreprise.getId());

        partialUpdatedEntreprise
            .companyName(UPDATED_COMPANY_NAME)
            .companyNeqNumber(UPDATED_COMPANY_NEQ_NUMBER)
            .isThistaxExemptOrganization(UPDATED_IS_THISTAX_EXEMPT_ORGANIZATION)
            .resourcePerson(UPDATED_RESOURCE_PERSON)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);

        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntreprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntreprise))
            )
            .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testEntreprise.getCompanyNeqNumber()).isEqualTo(UPDATED_COMPANY_NEQ_NUMBER);
        assertThat(testEntreprise.getIsThistaxExemptOrganization()).isEqualTo(UPDATED_IS_THISTAX_EXEMPT_ORGANIZATION);
        assertThat(testEntreprise.getResourcePerson()).isEqualTo(UPDATED_RESOURCE_PERSON);
        assertThat(testEntreprise.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEntreprise.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEntreprise.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entreprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeDelete = entrepriseRepository.findAll().size();

        // Delete the entreprise
        restEntrepriseMockMvc
            .perform(delete(ENTITY_API_URL_ID, entreprise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
