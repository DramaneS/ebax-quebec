package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Custumer;
import com.mycompany.myapp.repository.CustumerRepository;
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
 * Integration tests for the {@link CustumerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustumerResourceIT {

    private static final String DEFAULT_NAME_CUSTUMER = "AAAAAAAAAA";
    private static final String UPDATED_NAME_CUSTUMER = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/custumers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustumerRepository custumerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustumerMockMvc;

    private Custumer custumer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Custumer createEntity(EntityManager em) {
        Custumer custumer = new Custumer()
            .nameCustumer(DEFAULT_NAME_CUSTUMER)
            .adress(DEFAULT_ADRESS)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL);
        return custumer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Custumer createUpdatedEntity(EntityManager em) {
        Custumer custumer = new Custumer()
            .nameCustumer(UPDATED_NAME_CUSTUMER)
            .adress(UPDATED_ADRESS)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL);
        return custumer;
    }

    @BeforeEach
    public void initTest() {
        custumer = createEntity(em);
    }

    @Test
    @Transactional
    void createCustumer() throws Exception {
        int databaseSizeBeforeCreate = custumerRepository.findAll().size();
        // Create the Custumer
        restCustumerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custumer)))
            .andExpect(status().isCreated());

        // Validate the Custumer in the database
        List<Custumer> custumerList = custumerRepository.findAll();
        assertThat(custumerList).hasSize(databaseSizeBeforeCreate + 1);
        Custumer testCustumer = custumerList.get(custumerList.size() - 1);
        assertThat(testCustumer.getNameCustumer()).isEqualTo(DEFAULT_NAME_CUSTUMER);
        assertThat(testCustumer.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testCustumer.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCustumer.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createCustumerWithExistingId() throws Exception {
        // Create the Custumer with an existing ID
        custumer.setId(1L);

        int databaseSizeBeforeCreate = custumerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustumerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custumer)))
            .andExpect(status().isBadRequest());

        // Validate the Custumer in the database
        List<Custumer> custumerList = custumerRepository.findAll();
        assertThat(custumerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCustumers() throws Exception {
        // Initialize the database
        custumerRepository.saveAndFlush(custumer);

        // Get all the custumerList
        restCustumerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custumer.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameCustumer").value(hasItem(DEFAULT_NAME_CUSTUMER)))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getCustumer() throws Exception {
        // Initialize the database
        custumerRepository.saveAndFlush(custumer);

        // Get the custumer
        restCustumerMockMvc
            .perform(get(ENTITY_API_URL_ID, custumer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custumer.getId().intValue()))
            .andExpect(jsonPath("$.nameCustumer").value(DEFAULT_NAME_CUSTUMER))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingCustumer() throws Exception {
        // Get the custumer
        restCustumerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCustumer() throws Exception {
        // Initialize the database
        custumerRepository.saveAndFlush(custumer);

        int databaseSizeBeforeUpdate = custumerRepository.findAll().size();

        // Update the custumer
        Custumer updatedCustumer = custumerRepository.findById(custumer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCustumer are not directly saved in db
        em.detach(updatedCustumer);
        updatedCustumer.nameCustumer(UPDATED_NAME_CUSTUMER).adress(UPDATED_ADRESS).phone(UPDATED_PHONE).email(UPDATED_EMAIL);

        restCustumerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustumer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustumer))
            )
            .andExpect(status().isOk());

        // Validate the Custumer in the database
        List<Custumer> custumerList = custumerRepository.findAll();
        assertThat(custumerList).hasSize(databaseSizeBeforeUpdate);
        Custumer testCustumer = custumerList.get(custumerList.size() - 1);
        assertThat(testCustumer.getNameCustumer()).isEqualTo(UPDATED_NAME_CUSTUMER);
        assertThat(testCustumer.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testCustumer.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustumer.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingCustumer() throws Exception {
        int databaseSizeBeforeUpdate = custumerRepository.findAll().size();
        custumer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustumerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custumer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custumer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Custumer in the database
        List<Custumer> custumerList = custumerRepository.findAll();
        assertThat(custumerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustumer() throws Exception {
        int databaseSizeBeforeUpdate = custumerRepository.findAll().size();
        custumer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustumerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custumer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Custumer in the database
        List<Custumer> custumerList = custumerRepository.findAll();
        assertThat(custumerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustumer() throws Exception {
        int databaseSizeBeforeUpdate = custumerRepository.findAll().size();
        custumer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustumerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custumer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Custumer in the database
        List<Custumer> custumerList = custumerRepository.findAll();
        assertThat(custumerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustumerWithPatch() throws Exception {
        // Initialize the database
        custumerRepository.saveAndFlush(custumer);

        int databaseSizeBeforeUpdate = custumerRepository.findAll().size();

        // Update the custumer using partial update
        Custumer partialUpdatedCustumer = new Custumer();
        partialUpdatedCustumer.setId(custumer.getId());

        partialUpdatedCustumer.nameCustumer(UPDATED_NAME_CUSTUMER).phone(UPDATED_PHONE).email(UPDATED_EMAIL);

        restCustumerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustumer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustumer))
            )
            .andExpect(status().isOk());

        // Validate the Custumer in the database
        List<Custumer> custumerList = custumerRepository.findAll();
        assertThat(custumerList).hasSize(databaseSizeBeforeUpdate);
        Custumer testCustumer = custumerList.get(custumerList.size() - 1);
        assertThat(testCustumer.getNameCustumer()).isEqualTo(UPDATED_NAME_CUSTUMER);
        assertThat(testCustumer.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testCustumer.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustumer.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateCustumerWithPatch() throws Exception {
        // Initialize the database
        custumerRepository.saveAndFlush(custumer);

        int databaseSizeBeforeUpdate = custumerRepository.findAll().size();

        // Update the custumer using partial update
        Custumer partialUpdatedCustumer = new Custumer();
        partialUpdatedCustumer.setId(custumer.getId());

        partialUpdatedCustumer.nameCustumer(UPDATED_NAME_CUSTUMER).adress(UPDATED_ADRESS).phone(UPDATED_PHONE).email(UPDATED_EMAIL);

        restCustumerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustumer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustumer))
            )
            .andExpect(status().isOk());

        // Validate the Custumer in the database
        List<Custumer> custumerList = custumerRepository.findAll();
        assertThat(custumerList).hasSize(databaseSizeBeforeUpdate);
        Custumer testCustumer = custumerList.get(custumerList.size() - 1);
        assertThat(testCustumer.getNameCustumer()).isEqualTo(UPDATED_NAME_CUSTUMER);
        assertThat(testCustumer.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testCustumer.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustumer.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingCustumer() throws Exception {
        int databaseSizeBeforeUpdate = custumerRepository.findAll().size();
        custumer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustumerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custumer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custumer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Custumer in the database
        List<Custumer> custumerList = custumerRepository.findAll();
        assertThat(custumerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustumer() throws Exception {
        int databaseSizeBeforeUpdate = custumerRepository.findAll().size();
        custumer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustumerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custumer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Custumer in the database
        List<Custumer> custumerList = custumerRepository.findAll();
        assertThat(custumerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustumer() throws Exception {
        int databaseSizeBeforeUpdate = custumerRepository.findAll().size();
        custumer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustumerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custumer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Custumer in the database
        List<Custumer> custumerList = custumerRepository.findAll();
        assertThat(custumerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustumer() throws Exception {
        // Initialize the database
        custumerRepository.saveAndFlush(custumer);

        int databaseSizeBeforeDelete = custumerRepository.findAll().size();

        // Delete the custumer
        restCustumerMockMvc
            .perform(delete(ENTITY_API_URL_ID, custumer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Custumer> custumerList = custumerRepository.findAll();
        assertThat(custumerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
