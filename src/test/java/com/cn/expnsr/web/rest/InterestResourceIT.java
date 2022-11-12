package com.cn.expnsr.web.rest;

import static com.cn.expnsr.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cn.expnsr.IntegrationTest;
import com.cn.expnsr.domain.Interest;
import com.cn.expnsr.repository.InterestRepository;
import com.cn.expnsr.service.dto.InterestDTO;
import com.cn.expnsr.service.mapper.InterestMapper;
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
 * Integration tests for the {@link InterestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InterestResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_INTEREST_RATE = 1F;
    private static final Float UPDATED_INTEREST_RATE = 2F;

    private static final Float DEFAULT_TAX_RATE = 1F;
    private static final Float UPDATED_TAX_RATE = 2F;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX = new BigDecimal(2);

    private static final Boolean DEFAULT_VERIFIED = false;
    private static final Boolean UPDATED_VERIFIED = true;

    private static final String ENTITY_API_URL = "/api/interests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private InterestMapper interestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInterestMockMvc;

    private Interest interest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interest createEntity(EntityManager em) {
        Interest interest = new Interest()
            .date(DEFAULT_DATE)
            .interestRate(DEFAULT_INTEREST_RATE)
            .taxRate(DEFAULT_TAX_RATE)
            .amount(DEFAULT_AMOUNT)
            .tax(DEFAULT_TAX)
            .verified(DEFAULT_VERIFIED);
        return interest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interest createUpdatedEntity(EntityManager em) {
        Interest interest = new Interest()
            .date(UPDATED_DATE)
            .interestRate(UPDATED_INTEREST_RATE)
            .taxRate(UPDATED_TAX_RATE)
            .amount(UPDATED_AMOUNT)
            .tax(UPDATED_TAX)
            .verified(UPDATED_VERIFIED);
        return interest;
    }

    @BeforeEach
    public void initTest() {
        interest = createEntity(em);
    }

    @Test
    @Transactional
    void createInterest() throws Exception {
        int databaseSizeBeforeCreate = interestRepository.findAll().size();
        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);
        restInterestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestDTO)))
            .andExpect(status().isCreated());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeCreate + 1);
        Interest testInterest = interestList.get(interestList.size() - 1);
        assertThat(testInterest.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testInterest.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testInterest.getTaxRate()).isEqualTo(DEFAULT_TAX_RATE);
        assertThat(testInterest.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testInterest.getTax()).isEqualByComparingTo(DEFAULT_TAX);
        assertThat(testInterest.getVerified()).isEqualTo(DEFAULT_VERIFIED);
    }

    @Test
    @Transactional
    void createInterestWithExistingId() throws Exception {
        // Create the Interest with an existing ID
        interest.setId(1L);
        InterestDTO interestDTO = interestMapper.toDto(interest);

        int databaseSizeBeforeCreate = interestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = interestRepository.findAll().size();
        // set the field null
        interest.setDate(null);

        // Create the Interest, which fails.
        InterestDTO interestDTO = interestMapper.toDto(interest);

        restInterestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestDTO)))
            .andExpect(status().isBadRequest());

        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInterestRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = interestRepository.findAll().size();
        // set the field null
        interest.setInterestRate(null);

        // Create the Interest, which fails.
        InterestDTO interestDTO = interestMapper.toDto(interest);

        restInterestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestDTO)))
            .andExpect(status().isBadRequest());

        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = interestRepository.findAll().size();
        // set the field null
        interest.setTaxRate(null);

        // Create the Interest, which fails.
        InterestDTO interestDTO = interestMapper.toDto(interest);

        restInterestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestDTO)))
            .andExpect(status().isBadRequest());

        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = interestRepository.findAll().size();
        // set the field null
        interest.setAmount(null);

        // Create the Interest, which fails.
        InterestDTO interestDTO = interestMapper.toDto(interest);

        restInterestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestDTO)))
            .andExpect(status().isBadRequest());

        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = interestRepository.findAll().size();
        // set the field null
        interest.setTax(null);

        // Create the Interest, which fails.
        InterestDTO interestDTO = interestMapper.toDto(interest);

        restInterestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestDTO)))
            .andExpect(status().isBadRequest());

        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInterests() throws Exception {
        // Initialize the database
        interestRepository.saveAndFlush(interest);

        // Get all the interestList
        restInterestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interest.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(sameNumber(DEFAULT_TAX))))
            .andExpect(jsonPath("$.[*].verified").value(hasItem(DEFAULT_VERIFIED.booleanValue())));
    }

    @Test
    @Transactional
    void getInterest() throws Exception {
        // Initialize the database
        interestRepository.saveAndFlush(interest);

        // Get the interest
        restInterestMockMvc
            .perform(get(ENTITY_API_URL_ID, interest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interest.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.taxRate").value(DEFAULT_TAX_RATE.doubleValue()))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.tax").value(sameNumber(DEFAULT_TAX)))
            .andExpect(jsonPath("$.verified").value(DEFAULT_VERIFIED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingInterest() throws Exception {
        // Get the interest
        restInterestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInterest() throws Exception {
        // Initialize the database
        interestRepository.saveAndFlush(interest);

        int databaseSizeBeforeUpdate = interestRepository.findAll().size();

        // Update the interest
        Interest updatedInterest = interestRepository.findById(interest.getId()).get();
        // Disconnect from session so that the updates on updatedInterest are not directly saved in db
        em.detach(updatedInterest);
        updatedInterest
            .date(UPDATED_DATE)
            .interestRate(UPDATED_INTEREST_RATE)
            .taxRate(UPDATED_TAX_RATE)
            .amount(UPDATED_AMOUNT)
            .tax(UPDATED_TAX)
            .verified(UPDATED_VERIFIED);
        InterestDTO interestDTO = interestMapper.toDto(updatedInterest);

        restInterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, interestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interestDTO))
            )
            .andExpect(status().isOk());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeUpdate);
        Interest testInterest = interestList.get(interestList.size() - 1);
        assertThat(testInterest.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testInterest.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testInterest.getTaxRate()).isEqualTo(UPDATED_TAX_RATE);
        assertThat(testInterest.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testInterest.getTax()).isEqualByComparingTo(UPDATED_TAX);
        assertThat(testInterest.getVerified()).isEqualTo(UPDATED_VERIFIED);
    }

    @Test
    @Transactional
    void putNonExistingInterest() throws Exception {
        int databaseSizeBeforeUpdate = interestRepository.findAll().size();
        interest.setId(count.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, interestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInterest() throws Exception {
        int databaseSizeBeforeUpdate = interestRepository.findAll().size();
        interest.setId(count.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInterest() throws Exception {
        int databaseSizeBeforeUpdate = interestRepository.findAll().size();
        interest.setId(count.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInterestWithPatch() throws Exception {
        // Initialize the database
        interestRepository.saveAndFlush(interest);

        int databaseSizeBeforeUpdate = interestRepository.findAll().size();

        // Update the interest using partial update
        Interest partialUpdatedInterest = new Interest();
        partialUpdatedInterest.setId(interest.getId());

        partialUpdatedInterest.date(UPDATED_DATE);

        restInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInterest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInterest))
            )
            .andExpect(status().isOk());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeUpdate);
        Interest testInterest = interestList.get(interestList.size() - 1);
        assertThat(testInterest.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testInterest.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testInterest.getTaxRate()).isEqualTo(DEFAULT_TAX_RATE);
        assertThat(testInterest.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testInterest.getTax()).isEqualByComparingTo(DEFAULT_TAX);
        assertThat(testInterest.getVerified()).isEqualTo(DEFAULT_VERIFIED);
    }

    @Test
    @Transactional
    void fullUpdateInterestWithPatch() throws Exception {
        // Initialize the database
        interestRepository.saveAndFlush(interest);

        int databaseSizeBeforeUpdate = interestRepository.findAll().size();

        // Update the interest using partial update
        Interest partialUpdatedInterest = new Interest();
        partialUpdatedInterest.setId(interest.getId());

        partialUpdatedInterest
            .date(UPDATED_DATE)
            .interestRate(UPDATED_INTEREST_RATE)
            .taxRate(UPDATED_TAX_RATE)
            .amount(UPDATED_AMOUNT)
            .tax(UPDATED_TAX)
            .verified(UPDATED_VERIFIED);

        restInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInterest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInterest))
            )
            .andExpect(status().isOk());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeUpdate);
        Interest testInterest = interestList.get(interestList.size() - 1);
        assertThat(testInterest.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testInterest.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testInterest.getTaxRate()).isEqualTo(UPDATED_TAX_RATE);
        assertThat(testInterest.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testInterest.getTax()).isEqualByComparingTo(UPDATED_TAX);
        assertThat(testInterest.getVerified()).isEqualTo(UPDATED_VERIFIED);
    }

    @Test
    @Transactional
    void patchNonExistingInterest() throws Exception {
        int databaseSizeBeforeUpdate = interestRepository.findAll().size();
        interest.setId(count.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, interestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(interestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInterest() throws Exception {
        int databaseSizeBeforeUpdate = interestRepository.findAll().size();
        interest.setId(count.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(interestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInterest() throws Exception {
        int databaseSizeBeforeUpdate = interestRepository.findAll().size();
        interest.setId(count.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(interestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Interest in the database
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInterest() throws Exception {
        // Initialize the database
        interestRepository.saveAndFlush(interest);

        int databaseSizeBeforeDelete = interestRepository.findAll().size();

        // Delete the interest
        restInterestMockMvc
            .perform(delete(ENTITY_API_URL_ID, interest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Interest> interestList = interestRepository.findAll();
        assertThat(interestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
