package com.cn.expnsr.web.rest;

import static com.cn.expnsr.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cn.expnsr.IntegrationTest;
import com.cn.expnsr.domain.Deposit;
import com.cn.expnsr.repository.DepositRepository;
import com.cn.expnsr.service.dto.DepositDTO;
import com.cn.expnsr.service.mapper.DepositMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link DepositResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DepositResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Boolean DEFAULT_VERIFIED = false;
    private static final Boolean UPDATED_VERIFIED = true;

    private static final String ENTITY_API_URL = "/api/deposits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private DepositMapper depositMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepositMockMvc;

    private Deposit deposit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deposit createEntity(EntityManager em) {
        Deposit deposit = new Deposit().date(DEFAULT_DATE).amount(DEFAULT_AMOUNT).verified(DEFAULT_VERIFIED);
        return deposit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deposit createUpdatedEntity(EntityManager em) {
        Deposit deposit = new Deposit().date(UPDATED_DATE).amount(UPDATED_AMOUNT).verified(UPDATED_VERIFIED);
        return deposit;
    }

    @BeforeEach
    public void initTest() {
        deposit = createEntity(em);
    }

    @Test
    @Transactional
    void createDeposit() throws Exception {
        int databaseSizeBeforeCreate = depositRepository.findAll().size();
        // Create the Deposit
        DepositDTO depositDTO = depositMapper.toDto(deposit);
        restDepositMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(depositDTO)))
            .andExpect(status().isCreated());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeCreate + 1);
        Deposit testDeposit = depositList.get(depositList.size() - 1);
        assertThat(testDeposit.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDeposit.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testDeposit.getVerified()).isEqualTo(DEFAULT_VERIFIED);
    }

    @Test
    @Transactional
    void createDepositWithExistingId() throws Exception {
        // Create the Deposit with an existing ID
        deposit.setId(1L);
        DepositDTO depositDTO = depositMapper.toDto(deposit);

        int databaseSizeBeforeCreate = depositRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepositMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(depositDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositRepository.findAll().size();
        // set the field null
        deposit.setDate(null);

        // Create the Deposit, which fails.
        DepositDTO depositDTO = depositMapper.toDto(deposit);

        restDepositMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(depositDTO)))
            .andExpect(status().isBadRequest());

        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositRepository.findAll().size();
        // set the field null
        deposit.setAmount(null);

        // Create the Deposit, which fails.
        DepositDTO depositDTO = depositMapper.toDto(deposit);

        restDepositMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(depositDTO)))
            .andExpect(status().isBadRequest());

        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDeposits() throws Exception {
        // Initialize the database
        depositRepository.saveAndFlush(deposit);

        // Get all the depositList
        restDepositMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deposit.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].verified").value(hasItem(DEFAULT_VERIFIED.booleanValue())));
    }

    @Test
    @Transactional
    void getDeposit() throws Exception {
        // Initialize the database
        depositRepository.saveAndFlush(deposit);

        // Get the deposit
        restDepositMockMvc
            .perform(get(ENTITY_API_URL_ID, deposit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deposit.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.verified").value(DEFAULT_VERIFIED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingDeposit() throws Exception {
        // Get the deposit
        restDepositMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeposit() throws Exception {
        // Initialize the database
        depositRepository.saveAndFlush(deposit);

        int databaseSizeBeforeUpdate = depositRepository.findAll().size();

        // Update the deposit
        Deposit updatedDeposit = depositRepository.findById(deposit.getId()).get();
        // Disconnect from session so that the updates on updatedDeposit are not directly saved in db
        em.detach(updatedDeposit);
        updatedDeposit.date(UPDATED_DATE).amount(UPDATED_AMOUNT).verified(UPDATED_VERIFIED);
        DepositDTO depositDTO = depositMapper.toDto(updatedDeposit);

        restDepositMockMvc
            .perform(
                put(ENTITY_API_URL_ID, depositDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depositDTO))
            )
            .andExpect(status().isOk());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeUpdate);
        Deposit testDeposit = depositList.get(depositList.size() - 1);
        assertThat(testDeposit.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDeposit.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testDeposit.getVerified()).isEqualTo(UPDATED_VERIFIED);
    }

    @Test
    @Transactional
    void putNonExistingDeposit() throws Exception {
        int databaseSizeBeforeUpdate = depositRepository.findAll().size();
        deposit.setId(count.incrementAndGet());

        // Create the Deposit
        DepositDTO depositDTO = depositMapper.toDto(deposit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepositMockMvc
            .perform(
                put(ENTITY_API_URL_ID, depositDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depositDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeposit() throws Exception {
        int databaseSizeBeforeUpdate = depositRepository.findAll().size();
        deposit.setId(count.incrementAndGet());

        // Create the Deposit
        DepositDTO depositDTO = depositMapper.toDto(deposit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepositMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depositDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeposit() throws Exception {
        int databaseSizeBeforeUpdate = depositRepository.findAll().size();
        deposit.setId(count.incrementAndGet());

        // Create the Deposit
        DepositDTO depositDTO = depositMapper.toDto(deposit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepositMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(depositDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepositWithPatch() throws Exception {
        // Initialize the database
        depositRepository.saveAndFlush(deposit);

        int databaseSizeBeforeUpdate = depositRepository.findAll().size();

        // Update the deposit using partial update
        Deposit partialUpdatedDeposit = new Deposit();
        partialUpdatedDeposit.setId(deposit.getId());

        partialUpdatedDeposit.verified(UPDATED_VERIFIED);

        restDepositMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeposit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeposit))
            )
            .andExpect(status().isOk());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeUpdate);
        Deposit testDeposit = depositList.get(depositList.size() - 1);
        assertThat(testDeposit.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDeposit.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testDeposit.getVerified()).isEqualTo(UPDATED_VERIFIED);
    }

    @Test
    @Transactional
    void fullUpdateDepositWithPatch() throws Exception {
        // Initialize the database
        depositRepository.saveAndFlush(deposit);

        int databaseSizeBeforeUpdate = depositRepository.findAll().size();

        // Update the deposit using partial update
        Deposit partialUpdatedDeposit = new Deposit();
        partialUpdatedDeposit.setId(deposit.getId());

        partialUpdatedDeposit.date(UPDATED_DATE).amount(UPDATED_AMOUNT).verified(UPDATED_VERIFIED);

        restDepositMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeposit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeposit))
            )
            .andExpect(status().isOk());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeUpdate);
        Deposit testDeposit = depositList.get(depositList.size() - 1);
        assertThat(testDeposit.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDeposit.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testDeposit.getVerified()).isEqualTo(UPDATED_VERIFIED);
    }

    @Test
    @Transactional
    void patchNonExistingDeposit() throws Exception {
        int databaseSizeBeforeUpdate = depositRepository.findAll().size();
        deposit.setId(count.incrementAndGet());

        // Create the Deposit
        DepositDTO depositDTO = depositMapper.toDto(deposit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepositMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, depositDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(depositDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeposit() throws Exception {
        int databaseSizeBeforeUpdate = depositRepository.findAll().size();
        deposit.setId(count.incrementAndGet());

        // Create the Deposit
        DepositDTO depositDTO = depositMapper.toDto(deposit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepositMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(depositDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeposit() throws Exception {
        int databaseSizeBeforeUpdate = depositRepository.findAll().size();
        deposit.setId(count.incrementAndGet());

        // Create the Deposit
        DepositDTO depositDTO = depositMapper.toDto(deposit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepositMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(depositDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeposit() throws Exception {
        // Initialize the database
        depositRepository.saveAndFlush(deposit);

        int databaseSizeBeforeDelete = depositRepository.findAll().size();

        // Delete the deposit
        restDepositMockMvc
            .perform(delete(ENTITY_API_URL_ID, deposit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
