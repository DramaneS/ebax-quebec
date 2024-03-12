package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.HydroQuebecRate;
import com.mycompany.myapp.repository.HydroQuebecRateRepository;
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
 * Integration tests for the {@link HydroQuebecRateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HydroQuebecRateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hydro-quebec-rates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HydroQuebecRateRepository hydroQuebecRateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHydroQuebecRateMockMvc;

    private HydroQuebecRate hydroQuebecRate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HydroQuebecRate createEntity(EntityManager em) {
        HydroQuebecRate hydroQuebecRate = new HydroQuebecRate().name(DEFAULT_NAME);
        return hydroQuebecRate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HydroQuebecRate createUpdatedEntity(EntityManager em) {
        HydroQuebecRate hydroQuebecRate = new HydroQuebecRate().name(UPDATED_NAME);
        return hydroQuebecRate;
    }

    @BeforeEach
    public void initTest() {
        hydroQuebecRate = createEntity(em);
    }

    @Test
    @Transactional
    void createHydroQuebecRate() throws Exception {
        int databaseSizeBeforeCreate = hydroQuebecRateRepository.findAll().size();
        // Create the HydroQuebecRate
        restHydroQuebecRateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hydroQuebecRate))
            )
            .andExpect(status().isCreated());

        // Validate the HydroQuebecRate in the database
        List<HydroQuebecRate> hydroQuebecRateList = hydroQuebecRateRepository.findAll();
        assertThat(hydroQuebecRateList).hasSize(databaseSizeBeforeCreate + 1);
        HydroQuebecRate testHydroQuebecRate = hydroQuebecRateList.get(hydroQuebecRateList.size() - 1);
        assertThat(testHydroQuebecRate.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createHydroQuebecRateWithExistingId() throws Exception {
        // Create the HydroQuebecRate with an existing ID
        hydroQuebecRate.setId(1L);

        int databaseSizeBeforeCreate = hydroQuebecRateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHydroQuebecRateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hydroQuebecRate))
            )
            .andExpect(status().isBadRequest());

        // Validate the HydroQuebecRate in the database
        List<HydroQuebecRate> hydroQuebecRateList = hydroQuebecRateRepository.findAll();
        assertThat(hydroQuebecRateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHydroQuebecRates() throws Exception {
        // Initialize the database
        hydroQuebecRateRepository.saveAndFlush(hydroQuebecRate);

        // Get all the hydroQuebecRateList
        restHydroQuebecRateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hydroQuebecRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getHydroQuebecRate() throws Exception {
        // Initialize the database
        hydroQuebecRateRepository.saveAndFlush(hydroQuebecRate);

        // Get the hydroQuebecRate
        restHydroQuebecRateMockMvc
            .perform(get(ENTITY_API_URL_ID, hydroQuebecRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hydroQuebecRate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingHydroQuebecRate() throws Exception {
        // Get the hydroQuebecRate
        restHydroQuebecRateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHydroQuebecRate() throws Exception {
        // Initialize the database
        hydroQuebecRateRepository.saveAndFlush(hydroQuebecRate);

        int databaseSizeBeforeUpdate = hydroQuebecRateRepository.findAll().size();

        // Update the hydroQuebecRate
        HydroQuebecRate updatedHydroQuebecRate = hydroQuebecRateRepository.findById(hydroQuebecRate.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHydroQuebecRate are not directly saved in db
        em.detach(updatedHydroQuebecRate);
        updatedHydroQuebecRate.name(UPDATED_NAME);

        restHydroQuebecRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHydroQuebecRate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHydroQuebecRate))
            )
            .andExpect(status().isOk());

        // Validate the HydroQuebecRate in the database
        List<HydroQuebecRate> hydroQuebecRateList = hydroQuebecRateRepository.findAll();
        assertThat(hydroQuebecRateList).hasSize(databaseSizeBeforeUpdate);
        HydroQuebecRate testHydroQuebecRate = hydroQuebecRateList.get(hydroQuebecRateList.size() - 1);
        assertThat(testHydroQuebecRate.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingHydroQuebecRate() throws Exception {
        int databaseSizeBeforeUpdate = hydroQuebecRateRepository.findAll().size();
        hydroQuebecRate.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHydroQuebecRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hydroQuebecRate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hydroQuebecRate))
            )
            .andExpect(status().isBadRequest());

        // Validate the HydroQuebecRate in the database
        List<HydroQuebecRate> hydroQuebecRateList = hydroQuebecRateRepository.findAll();
        assertThat(hydroQuebecRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHydroQuebecRate() throws Exception {
        int databaseSizeBeforeUpdate = hydroQuebecRateRepository.findAll().size();
        hydroQuebecRate.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHydroQuebecRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hydroQuebecRate))
            )
            .andExpect(status().isBadRequest());

        // Validate the HydroQuebecRate in the database
        List<HydroQuebecRate> hydroQuebecRateList = hydroQuebecRateRepository.findAll();
        assertThat(hydroQuebecRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHydroQuebecRate() throws Exception {
        int databaseSizeBeforeUpdate = hydroQuebecRateRepository.findAll().size();
        hydroQuebecRate.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHydroQuebecRateMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hydroQuebecRate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HydroQuebecRate in the database
        List<HydroQuebecRate> hydroQuebecRateList = hydroQuebecRateRepository.findAll();
        assertThat(hydroQuebecRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHydroQuebecRateWithPatch() throws Exception {
        // Initialize the database
        hydroQuebecRateRepository.saveAndFlush(hydroQuebecRate);

        int databaseSizeBeforeUpdate = hydroQuebecRateRepository.findAll().size();

        // Update the hydroQuebecRate using partial update
        HydroQuebecRate partialUpdatedHydroQuebecRate = new HydroQuebecRate();
        partialUpdatedHydroQuebecRate.setId(hydroQuebecRate.getId());

        restHydroQuebecRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHydroQuebecRate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHydroQuebecRate))
            )
            .andExpect(status().isOk());

        // Validate the HydroQuebecRate in the database
        List<HydroQuebecRate> hydroQuebecRateList = hydroQuebecRateRepository.findAll();
        assertThat(hydroQuebecRateList).hasSize(databaseSizeBeforeUpdate);
        HydroQuebecRate testHydroQuebecRate = hydroQuebecRateList.get(hydroQuebecRateList.size() - 1);
        assertThat(testHydroQuebecRate.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateHydroQuebecRateWithPatch() throws Exception {
        // Initialize the database
        hydroQuebecRateRepository.saveAndFlush(hydroQuebecRate);

        int databaseSizeBeforeUpdate = hydroQuebecRateRepository.findAll().size();

        // Update the hydroQuebecRate using partial update
        HydroQuebecRate partialUpdatedHydroQuebecRate = new HydroQuebecRate();
        partialUpdatedHydroQuebecRate.setId(hydroQuebecRate.getId());

        partialUpdatedHydroQuebecRate.name(UPDATED_NAME);

        restHydroQuebecRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHydroQuebecRate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHydroQuebecRate))
            )
            .andExpect(status().isOk());

        // Validate the HydroQuebecRate in the database
        List<HydroQuebecRate> hydroQuebecRateList = hydroQuebecRateRepository.findAll();
        assertThat(hydroQuebecRateList).hasSize(databaseSizeBeforeUpdate);
        HydroQuebecRate testHydroQuebecRate = hydroQuebecRateList.get(hydroQuebecRateList.size() - 1);
        assertThat(testHydroQuebecRate.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingHydroQuebecRate() throws Exception {
        int databaseSizeBeforeUpdate = hydroQuebecRateRepository.findAll().size();
        hydroQuebecRate.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHydroQuebecRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hydroQuebecRate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hydroQuebecRate))
            )
            .andExpect(status().isBadRequest());

        // Validate the HydroQuebecRate in the database
        List<HydroQuebecRate> hydroQuebecRateList = hydroQuebecRateRepository.findAll();
        assertThat(hydroQuebecRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHydroQuebecRate() throws Exception {
        int databaseSizeBeforeUpdate = hydroQuebecRateRepository.findAll().size();
        hydroQuebecRate.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHydroQuebecRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hydroQuebecRate))
            )
            .andExpect(status().isBadRequest());

        // Validate the HydroQuebecRate in the database
        List<HydroQuebecRate> hydroQuebecRateList = hydroQuebecRateRepository.findAll();
        assertThat(hydroQuebecRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHydroQuebecRate() throws Exception {
        int databaseSizeBeforeUpdate = hydroQuebecRateRepository.findAll().size();
        hydroQuebecRate.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHydroQuebecRateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hydroQuebecRate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HydroQuebecRate in the database
        List<HydroQuebecRate> hydroQuebecRateList = hydroQuebecRateRepository.findAll();
        assertThat(hydroQuebecRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHydroQuebecRate() throws Exception {
        // Initialize the database
        hydroQuebecRateRepository.saveAndFlush(hydroQuebecRate);

        int databaseSizeBeforeDelete = hydroQuebecRateRepository.findAll().size();

        // Delete the hydroQuebecRate
        restHydroQuebecRateMockMvc
            .perform(delete(ENTITY_API_URL_ID, hydroQuebecRate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HydroQuebecRate> hydroQuebecRateList = hydroQuebecRateRepository.findAll();
        assertThat(hydroQuebecRateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
