package com.cn.expnsr.web.rest;

import static com.cn.expnsr.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cn.expnsr.IntegrationTest;
import com.cn.expnsr.domain.RecurringBills;
import com.cn.expnsr.repository.RecurringBillsRepository;
import com.cn.expnsr.service.dto.RecurringBillsDTO;
import com.cn.expnsr.service.mapper.RecurringBillsMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RecurringBillsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecurringBillsResourceIT {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Integer DEFAULT_TENURE = 1;
    private static final Integer UPDATED_TENURE = 2;

    private static final String DEFAULT_PRIMARY_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_SECONDARY_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_CATEGORY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/recurring-bills";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RecurringBillsRepository recurringBillsRepository;

    @Autowired
    private RecurringBillsMapper recurringBillsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecurringBillsMockMvc;

    private RecurringBills recurringBills;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecurringBills createEntity(EntityManager em) {
        RecurringBills recurringBills = new RecurringBills()
            .amount(DEFAULT_AMOUNT)
            .tenure(DEFAULT_TENURE)
            .primaryCategory(DEFAULT_PRIMARY_CATEGORY)
            .secondaryCategory(DEFAULT_SECONDARY_CATEGORY);
        return recurringBills;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecurringBills createUpdatedEntity(EntityManager em) {
        RecurringBills recurringBills = new RecurringBills()
            .amount(UPDATED_AMOUNT)
            .tenure(UPDATED_TENURE)
            .primaryCategory(UPDATED_PRIMARY_CATEGORY)
            .secondaryCategory(UPDATED_SECONDARY_CATEGORY);
        return recurringBills;
    }

    @BeforeEach
    public void initTest() {
        recurringBills = createEntity(em);
    }

    @Test
    @Transactional
    void createRecurringBills() throws Exception {
        int databaseSizeBeforeCreate = recurringBillsRepository.findAll().size();
        // Create the RecurringBills
        RecurringBillsDTO recurringBillsDTO = recurringBillsMapper.toDto(recurringBills);
        restRecurringBillsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recurringBillsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RecurringBills in the database
        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeCreate + 1);
        RecurringBills testRecurringBills = recurringBillsList.get(recurringBillsList.size() - 1);
        assertThat(testRecurringBills.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testRecurringBills.getTenure()).isEqualTo(DEFAULT_TENURE);
        assertThat(testRecurringBills.getPrimaryCategory()).isEqualTo(DEFAULT_PRIMARY_CATEGORY);
        assertThat(testRecurringBills.getSecondaryCategory()).isEqualTo(DEFAULT_SECONDARY_CATEGORY);
    }

    @Test
    @Transactional
    void createRecurringBillsWithExistingId() throws Exception {
        // Create the RecurringBills with an existing ID
        recurringBills.setId(1L);
        RecurringBillsDTO recurringBillsDTO = recurringBillsMapper.toDto(recurringBills);

        int databaseSizeBeforeCreate = recurringBillsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecurringBillsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recurringBillsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecurringBills in the database
        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = recurringBillsRepository.findAll().size();
        // set the field null
        recurringBills.setAmount(null);

        // Create the RecurringBills, which fails.
        RecurringBillsDTO recurringBillsDTO = recurringBillsMapper.toDto(recurringBills);

        restRecurringBillsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recurringBillsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTenureIsRequired() throws Exception {
        int databaseSizeBeforeTest = recurringBillsRepository.findAll().size();
        // set the field null
        recurringBills.setTenure(null);

        // Create the RecurringBills, which fails.
        RecurringBillsDTO recurringBillsDTO = recurringBillsMapper.toDto(recurringBills);

        restRecurringBillsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recurringBillsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrimaryCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = recurringBillsRepository.findAll().size();
        // set the field null
        recurringBills.setPrimaryCategory(null);

        // Create the RecurringBills, which fails.
        RecurringBillsDTO recurringBillsDTO = recurringBillsMapper.toDto(recurringBills);

        restRecurringBillsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recurringBillsDTO))
            )
            .andExpect(status().isBadRequest());

        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRecurringBills() throws Exception {
        // Initialize the database
        recurringBillsRepository.saveAndFlush(recurringBills);

        // Get all the recurringBillsList
        restRecurringBillsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recurringBills.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].tenure").value(hasItem(DEFAULT_TENURE)))
            .andExpect(jsonPath("$.[*].primaryCategory").value(hasItem(DEFAULT_PRIMARY_CATEGORY)))
            .andExpect(jsonPath("$.[*].secondaryCategory").value(hasItem(DEFAULT_SECONDARY_CATEGORY)));
    }

    @Test
    @Transactional
    void getRecurringBills() throws Exception {
        // Initialize the database
        recurringBillsRepository.saveAndFlush(recurringBills);

        // Get the recurringBills
        restRecurringBillsMockMvc
            .perform(get(ENTITY_API_URL_ID, recurringBills.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recurringBills.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.tenure").value(DEFAULT_TENURE))
            .andExpect(jsonPath("$.primaryCategory").value(DEFAULT_PRIMARY_CATEGORY))
            .andExpect(jsonPath("$.secondaryCategory").value(DEFAULT_SECONDARY_CATEGORY));
    }

    @Test
    @Transactional
    void getNonExistingRecurringBills() throws Exception {
        // Get the recurringBills
        restRecurringBillsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecurringBills() throws Exception {
        // Initialize the database
        recurringBillsRepository.saveAndFlush(recurringBills);

        int databaseSizeBeforeUpdate = recurringBillsRepository.findAll().size();

        // Update the recurringBills
        RecurringBills updatedRecurringBills = recurringBillsRepository.findById(recurringBills.getId()).get();
        // Disconnect from session so that the updates on updatedRecurringBills are not directly saved in db
        em.detach(updatedRecurringBills);
        updatedRecurringBills
            .amount(UPDATED_AMOUNT)
            .tenure(UPDATED_TENURE)
            .primaryCategory(UPDATED_PRIMARY_CATEGORY)
            .secondaryCategory(UPDATED_SECONDARY_CATEGORY);
        RecurringBillsDTO recurringBillsDTO = recurringBillsMapper.toDto(updatedRecurringBills);

        restRecurringBillsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recurringBillsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recurringBillsDTO))
            )
            .andExpect(status().isOk());

        // Validate the RecurringBills in the database
        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeUpdate);
        RecurringBills testRecurringBills = recurringBillsList.get(recurringBillsList.size() - 1);
        assertThat(testRecurringBills.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testRecurringBills.getTenure()).isEqualTo(UPDATED_TENURE);
        assertThat(testRecurringBills.getPrimaryCategory()).isEqualTo(UPDATED_PRIMARY_CATEGORY);
        assertThat(testRecurringBills.getSecondaryCategory()).isEqualTo(UPDATED_SECONDARY_CATEGORY);
    }

    @Test
    @Transactional
    void putNonExistingRecurringBills() throws Exception {
        int databaseSizeBeforeUpdate = recurringBillsRepository.findAll().size();
        recurringBills.setId(count.incrementAndGet());

        // Create the RecurringBills
        RecurringBillsDTO recurringBillsDTO = recurringBillsMapper.toDto(recurringBills);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecurringBillsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recurringBillsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recurringBillsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecurringBills in the database
        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecurringBills() throws Exception {
        int databaseSizeBeforeUpdate = recurringBillsRepository.findAll().size();
        recurringBills.setId(count.incrementAndGet());

        // Create the RecurringBills
        RecurringBillsDTO recurringBillsDTO = recurringBillsMapper.toDto(recurringBills);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecurringBillsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recurringBillsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecurringBills in the database
        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecurringBills() throws Exception {
        int databaseSizeBeforeUpdate = recurringBillsRepository.findAll().size();
        recurringBills.setId(count.incrementAndGet());

        // Create the RecurringBills
        RecurringBillsDTO recurringBillsDTO = recurringBillsMapper.toDto(recurringBills);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecurringBillsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recurringBillsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecurringBills in the database
        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecurringBillsWithPatch() throws Exception {
        // Initialize the database
        recurringBillsRepository.saveAndFlush(recurringBills);

        int databaseSizeBeforeUpdate = recurringBillsRepository.findAll().size();

        // Update the recurringBills using partial update
        RecurringBills partialUpdatedRecurringBills = new RecurringBills();
        partialUpdatedRecurringBills.setId(recurringBills.getId());

        partialUpdatedRecurringBills.tenure(UPDATED_TENURE).primaryCategory(UPDATED_PRIMARY_CATEGORY);

        restRecurringBillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecurringBills.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecurringBills))
            )
            .andExpect(status().isOk());

        // Validate the RecurringBills in the database
        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeUpdate);
        RecurringBills testRecurringBills = recurringBillsList.get(recurringBillsList.size() - 1);
        assertThat(testRecurringBills.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testRecurringBills.getTenure()).isEqualTo(UPDATED_TENURE);
        assertThat(testRecurringBills.getPrimaryCategory()).isEqualTo(UPDATED_PRIMARY_CATEGORY);
        assertThat(testRecurringBills.getSecondaryCategory()).isEqualTo(DEFAULT_SECONDARY_CATEGORY);
    }

    @Test
    @Transactional
    void fullUpdateRecurringBillsWithPatch() throws Exception {
        // Initialize the database
        recurringBillsRepository.saveAndFlush(recurringBills);

        int databaseSizeBeforeUpdate = recurringBillsRepository.findAll().size();

        // Update the recurringBills using partial update
        RecurringBills partialUpdatedRecurringBills = new RecurringBills();
        partialUpdatedRecurringBills.setId(recurringBills.getId());

        partialUpdatedRecurringBills
            .amount(UPDATED_AMOUNT)
            .tenure(UPDATED_TENURE)
            .primaryCategory(UPDATED_PRIMARY_CATEGORY)
            .secondaryCategory(UPDATED_SECONDARY_CATEGORY);

        restRecurringBillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecurringBills.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecurringBills))
            )
            .andExpect(status().isOk());

        // Validate the RecurringBills in the database
        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeUpdate);
        RecurringBills testRecurringBills = recurringBillsList.get(recurringBillsList.size() - 1);
        assertThat(testRecurringBills.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testRecurringBills.getTenure()).isEqualTo(UPDATED_TENURE);
        assertThat(testRecurringBills.getPrimaryCategory()).isEqualTo(UPDATED_PRIMARY_CATEGORY);
        assertThat(testRecurringBills.getSecondaryCategory()).isEqualTo(UPDATED_SECONDARY_CATEGORY);
    }

    @Test
    @Transactional
    void patchNonExistingRecurringBills() throws Exception {
        int databaseSizeBeforeUpdate = recurringBillsRepository.findAll().size();
        recurringBills.setId(count.incrementAndGet());

        // Create the RecurringBills
        RecurringBillsDTO recurringBillsDTO = recurringBillsMapper.toDto(recurringBills);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecurringBillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recurringBillsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recurringBillsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecurringBills in the database
        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecurringBills() throws Exception {
        int databaseSizeBeforeUpdate = recurringBillsRepository.findAll().size();
        recurringBills.setId(count.incrementAndGet());

        // Create the RecurringBills
        RecurringBillsDTO recurringBillsDTO = recurringBillsMapper.toDto(recurringBills);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecurringBillsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recurringBillsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecurringBills in the database
        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecurringBills() throws Exception {
        int databaseSizeBeforeUpdate = recurringBillsRepository.findAll().size();
        recurringBills.setId(count.incrementAndGet());

        // Create the RecurringBills
        RecurringBillsDTO recurringBillsDTO = recurringBillsMapper.toDto(recurringBills);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecurringBillsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recurringBillsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecurringBills in the database
        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecurringBills() throws Exception {
        // Initialize the database
        recurringBillsRepository.saveAndFlush(recurringBills);

        int databaseSizeBeforeDelete = recurringBillsRepository.findAll().size();

        // Delete the recurringBills
        restRecurringBillsMockMvc
            .perform(delete(ENTITY_API_URL_ID, recurringBills.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecurringBills> recurringBillsList = recurringBillsRepository.findAll();
        assertThat(recurringBillsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
