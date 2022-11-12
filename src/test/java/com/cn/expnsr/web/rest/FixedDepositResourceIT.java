package com.cn.expnsr.web.rest;

import static com.cn.expnsr.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cn.expnsr.IntegrationTest;
import com.cn.expnsr.domain.FixedDeposit;
import com.cn.expnsr.domain.enumeration.FDStatus;
import com.cn.expnsr.domain.enumeration.FDType;
import com.cn.expnsr.repository.FixedDepositRepository;
import com.cn.expnsr.service.dto.FixedDepositDTO;
import com.cn.expnsr.service.mapper.FixedDepositMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
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
 * Integration tests for the {@link FixedDepositResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FixedDepositResourceIT {

    private static final FDType DEFAULT_TYPE = FDType.BSP;
    private static final FDType UPDATED_TYPE = FDType.PSP;

    private static final Instant DEFAULT_ISSUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ISSUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_RENEW_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_RENEW_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MATURITY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MATURITY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_INTEREST_RATE = 1F;
    private static final Float UPDATED_INTEREST_RATE = 2F;

    private static final Float DEFAULT_TAX_RATE = 1F;
    private static final Float UPDATED_TAX_RATE = 2F;

    private static final Integer DEFAULT_TENURE = 1;
    private static final Integer UPDATED_TENURE = 2;

    private static final Integer DEFAULT_INTEREST_TENURE = 1;
    private static final Integer UPDATED_INTEREST_TENURE = 2;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MATURITY_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MATURITY_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTHLY_DEPOSIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTHLY_DEPOSIT = new BigDecimal(2);

    private static final Instant DEFAULT_MONTHLY_DEPOSIT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MONTHLY_DEPOSIT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BANK = "AAAAAAAAAA";
    private static final String UPDATED_BANK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTO_RENEW = false;
    private static final Boolean UPDATED_AUTO_RENEW = true;

    private static final Boolean DEFAULT_RENEW_WITH_INTEREST = false;
    private static final Boolean UPDATED_RENEW_WITH_INTEREST = true;

    private static final FDStatus DEFAULT_STATUS = FDStatus.ACTIVE;
    private static final FDStatus UPDATED_STATUS = FDStatus.MATURED;

    private static final String ENTITY_API_URL = "/api/fixed-deposits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FixedDepositRepository fixedDepositRepository;

    @Autowired
    private FixedDepositMapper fixedDepositMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFixedDepositMockMvc;

    private FixedDeposit fixedDeposit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FixedDeposit createEntity(EntityManager em) {
        FixedDeposit fixedDeposit = new FixedDeposit()
            .type(DEFAULT_TYPE)
            .issueDate(DEFAULT_ISSUE_DATE)
            .lastRenewDate(DEFAULT_LAST_RENEW_DATE)
            .maturityDate(DEFAULT_MATURITY_DATE)
            .interestRate(DEFAULT_INTEREST_RATE)
            .taxRate(DEFAULT_TAX_RATE)
            .tenure(DEFAULT_TENURE)
            .interestTenure(DEFAULT_INTEREST_TENURE)
            .amount(DEFAULT_AMOUNT)
            .maturityAmount(DEFAULT_MATURITY_AMOUNT)
            .tax(DEFAULT_TAX)
            .monthlyDeposit(DEFAULT_MONTHLY_DEPOSIT)
            .monthlyDepositDate(DEFAULT_MONTHLY_DEPOSIT_DATE)
            .bank(DEFAULT_BANK)
            .autoRenew(DEFAULT_AUTO_RENEW)
            .renewWithInterest(DEFAULT_RENEW_WITH_INTEREST)
            .status(DEFAULT_STATUS);
        return fixedDeposit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FixedDeposit createUpdatedEntity(EntityManager em) {
        FixedDeposit fixedDeposit = new FixedDeposit()
            .type(UPDATED_TYPE)
            .issueDate(UPDATED_ISSUE_DATE)
            .lastRenewDate(UPDATED_LAST_RENEW_DATE)
            .maturityDate(UPDATED_MATURITY_DATE)
            .interestRate(UPDATED_INTEREST_RATE)
            .taxRate(UPDATED_TAX_RATE)
            .tenure(UPDATED_TENURE)
            .interestTenure(UPDATED_INTEREST_TENURE)
            .amount(UPDATED_AMOUNT)
            .maturityAmount(UPDATED_MATURITY_AMOUNT)
            .tax(UPDATED_TAX)
            .monthlyDeposit(UPDATED_MONTHLY_DEPOSIT)
            .monthlyDepositDate(UPDATED_MONTHLY_DEPOSIT_DATE)
            .bank(UPDATED_BANK)
            .autoRenew(UPDATED_AUTO_RENEW)
            .renewWithInterest(UPDATED_RENEW_WITH_INTEREST)
            .status(UPDATED_STATUS);
        return fixedDeposit;
    }

    @BeforeEach
    public void initTest() {
        fixedDeposit = createEntity(em);
    }

    @Test
    @Transactional
    void createFixedDeposit() throws Exception {
        int databaseSizeBeforeCreate = fixedDepositRepository.findAll().size();
        // Create the FixedDeposit
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);
        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeCreate + 1);
        FixedDeposit testFixedDeposit = fixedDepositList.get(fixedDepositList.size() - 1);
        assertThat(testFixedDeposit.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFixedDeposit.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testFixedDeposit.getLastRenewDate()).isEqualTo(DEFAULT_LAST_RENEW_DATE);
        assertThat(testFixedDeposit.getMaturityDate()).isEqualTo(DEFAULT_MATURITY_DATE);
        assertThat(testFixedDeposit.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testFixedDeposit.getTaxRate()).isEqualTo(DEFAULT_TAX_RATE);
        assertThat(testFixedDeposit.getTenure()).isEqualTo(DEFAULT_TENURE);
        assertThat(testFixedDeposit.getInterestTenure()).isEqualTo(DEFAULT_INTEREST_TENURE);
        assertThat(testFixedDeposit.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testFixedDeposit.getMaturityAmount()).isEqualByComparingTo(DEFAULT_MATURITY_AMOUNT);
        assertThat(testFixedDeposit.getTax()).isEqualByComparingTo(DEFAULT_TAX);
        assertThat(testFixedDeposit.getMonthlyDeposit()).isEqualByComparingTo(DEFAULT_MONTHLY_DEPOSIT);
        assertThat(testFixedDeposit.getMonthlyDepositDate()).isEqualTo(DEFAULT_MONTHLY_DEPOSIT_DATE);
        assertThat(testFixedDeposit.getBank()).isEqualTo(DEFAULT_BANK);
        assertThat(testFixedDeposit.getAutoRenew()).isEqualTo(DEFAULT_AUTO_RENEW);
        assertThat(testFixedDeposit.getRenewWithInterest()).isEqualTo(DEFAULT_RENEW_WITH_INTEREST);
        assertThat(testFixedDeposit.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createFixedDepositWithExistingId() throws Exception {
        // Create the FixedDeposit with an existing ID
        fixedDeposit.setId("existing_id");
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        int databaseSizeBeforeCreate = fixedDepositRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setType(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIssueDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setIssueDate(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaturityDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setMaturityDate(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInterestRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setInterestRate(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setTaxRate(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTenureIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setTenure(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInterestTenureIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setInterestTenure(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setAmount(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaturityAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setMaturityAmount(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setTax(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMonthlyDepositIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setMonthlyDeposit(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBankIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setBank(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAutoRenewIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setAutoRenew(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRenewWithInterestIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setRenewWithInterest(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setStatus(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFixedDeposits() throws Exception {
        // Initialize the database
        fixedDeposit.setId(UUID.randomUUID().toString());
        fixedDepositRepository.saveAndFlush(fixedDeposit);

        // Get all the fixedDepositList
        restFixedDepositMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixedDeposit.getId())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastRenewDate").value(hasItem(DEFAULT_LAST_RENEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].maturityDate").value(hasItem(DEFAULT_MATURITY_DATE.toString())))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].tenure").value(hasItem(DEFAULT_TENURE)))
            .andExpect(jsonPath("$.[*].interestTenure").value(hasItem(DEFAULT_INTEREST_TENURE)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].maturityAmount").value(hasItem(sameNumber(DEFAULT_MATURITY_AMOUNT))))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(sameNumber(DEFAULT_TAX))))
            .andExpect(jsonPath("$.[*].monthlyDeposit").value(hasItem(sameNumber(DEFAULT_MONTHLY_DEPOSIT))))
            .andExpect(jsonPath("$.[*].monthlyDepositDate").value(hasItem(DEFAULT_MONTHLY_DEPOSIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK)))
            .andExpect(jsonPath("$.[*].autoRenew").value(hasItem(DEFAULT_AUTO_RENEW.booleanValue())))
            .andExpect(jsonPath("$.[*].renewWithInterest").value(hasItem(DEFAULT_RENEW_WITH_INTEREST.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getFixedDeposit() throws Exception {
        // Initialize the database
        fixedDeposit.setId(UUID.randomUUID().toString());
        fixedDepositRepository.saveAndFlush(fixedDeposit);

        // Get the fixedDeposit
        restFixedDepositMockMvc
            .perform(get(ENTITY_API_URL_ID, fixedDeposit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fixedDeposit.getId()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.issueDate").value(DEFAULT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.lastRenewDate").value(DEFAULT_LAST_RENEW_DATE.toString()))
            .andExpect(jsonPath("$.maturityDate").value(DEFAULT_MATURITY_DATE.toString()))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.taxRate").value(DEFAULT_TAX_RATE.doubleValue()))
            .andExpect(jsonPath("$.tenure").value(DEFAULT_TENURE))
            .andExpect(jsonPath("$.interestTenure").value(DEFAULT_INTEREST_TENURE))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.maturityAmount").value(sameNumber(DEFAULT_MATURITY_AMOUNT)))
            .andExpect(jsonPath("$.tax").value(sameNumber(DEFAULT_TAX)))
            .andExpect(jsonPath("$.monthlyDeposit").value(sameNumber(DEFAULT_MONTHLY_DEPOSIT)))
            .andExpect(jsonPath("$.monthlyDepositDate").value(DEFAULT_MONTHLY_DEPOSIT_DATE.toString()))
            .andExpect(jsonPath("$.bank").value(DEFAULT_BANK))
            .andExpect(jsonPath("$.autoRenew").value(DEFAULT_AUTO_RENEW.booleanValue()))
            .andExpect(jsonPath("$.renewWithInterest").value(DEFAULT_RENEW_WITH_INTEREST.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFixedDeposit() throws Exception {
        // Get the fixedDeposit
        restFixedDepositMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFixedDeposit() throws Exception {
        // Initialize the database
        fixedDeposit.setId(UUID.randomUUID().toString());
        fixedDepositRepository.saveAndFlush(fixedDeposit);

        int databaseSizeBeforeUpdate = fixedDepositRepository.findAll().size();

        // Update the fixedDeposit
        FixedDeposit updatedFixedDeposit = fixedDepositRepository.findById(fixedDeposit.getId()).get();
        // Disconnect from session so that the updates on updatedFixedDeposit are not directly saved in db
        em.detach(updatedFixedDeposit);
        updatedFixedDeposit
            .type(UPDATED_TYPE)
            .issueDate(UPDATED_ISSUE_DATE)
            .lastRenewDate(UPDATED_LAST_RENEW_DATE)
            .maturityDate(UPDATED_MATURITY_DATE)
            .interestRate(UPDATED_INTEREST_RATE)
            .taxRate(UPDATED_TAX_RATE)
            .tenure(UPDATED_TENURE)
            .interestTenure(UPDATED_INTEREST_TENURE)
            .amount(UPDATED_AMOUNT)
            .maturityAmount(UPDATED_MATURITY_AMOUNT)
            .tax(UPDATED_TAX)
            .monthlyDeposit(UPDATED_MONTHLY_DEPOSIT)
            .monthlyDepositDate(UPDATED_MONTHLY_DEPOSIT_DATE)
            .bank(UPDATED_BANK)
            .autoRenew(UPDATED_AUTO_RENEW)
            .renewWithInterest(UPDATED_RENEW_WITH_INTEREST)
            .status(UPDATED_STATUS);
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(updatedFixedDeposit);

        restFixedDepositMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fixedDepositDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isOk());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeUpdate);
        FixedDeposit testFixedDeposit = fixedDepositList.get(fixedDepositList.size() - 1);
        assertThat(testFixedDeposit.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFixedDeposit.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testFixedDeposit.getLastRenewDate()).isEqualTo(UPDATED_LAST_RENEW_DATE);
        assertThat(testFixedDeposit.getMaturityDate()).isEqualTo(UPDATED_MATURITY_DATE);
        assertThat(testFixedDeposit.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testFixedDeposit.getTaxRate()).isEqualTo(UPDATED_TAX_RATE);
        assertThat(testFixedDeposit.getTenure()).isEqualTo(UPDATED_TENURE);
        assertThat(testFixedDeposit.getInterestTenure()).isEqualTo(UPDATED_INTEREST_TENURE);
        assertThat(testFixedDeposit.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testFixedDeposit.getMaturityAmount()).isEqualByComparingTo(UPDATED_MATURITY_AMOUNT);
        assertThat(testFixedDeposit.getTax()).isEqualByComparingTo(UPDATED_TAX);
        assertThat(testFixedDeposit.getMonthlyDeposit()).isEqualByComparingTo(UPDATED_MONTHLY_DEPOSIT);
        assertThat(testFixedDeposit.getMonthlyDepositDate()).isEqualTo(UPDATED_MONTHLY_DEPOSIT_DATE);
        assertThat(testFixedDeposit.getBank()).isEqualTo(UPDATED_BANK);
        assertThat(testFixedDeposit.getAutoRenew()).isEqualTo(UPDATED_AUTO_RENEW);
        assertThat(testFixedDeposit.getRenewWithInterest()).isEqualTo(UPDATED_RENEW_WITH_INTEREST);
        assertThat(testFixedDeposit.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingFixedDeposit() throws Exception {
        int databaseSizeBeforeUpdate = fixedDepositRepository.findAll().size();
        fixedDeposit.setId(UUID.randomUUID().toString());

        // Create the FixedDeposit
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFixedDepositMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fixedDepositDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFixedDeposit() throws Exception {
        int databaseSizeBeforeUpdate = fixedDepositRepository.findAll().size();
        fixedDeposit.setId(UUID.randomUUID().toString());

        // Create the FixedDeposit
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFixedDepositMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFixedDeposit() throws Exception {
        int databaseSizeBeforeUpdate = fixedDepositRepository.findAll().size();
        fixedDeposit.setId(UUID.randomUUID().toString());

        // Create the FixedDeposit
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFixedDepositMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFixedDepositWithPatch() throws Exception {
        // Initialize the database
        fixedDeposit.setId(UUID.randomUUID().toString());
        fixedDepositRepository.saveAndFlush(fixedDeposit);

        int databaseSizeBeforeUpdate = fixedDepositRepository.findAll().size();

        // Update the fixedDeposit using partial update
        FixedDeposit partialUpdatedFixedDeposit = new FixedDeposit();
        partialUpdatedFixedDeposit.setId(fixedDeposit.getId());

        partialUpdatedFixedDeposit
            .issueDate(UPDATED_ISSUE_DATE)
            .lastRenewDate(UPDATED_LAST_RENEW_DATE)
            .maturityDate(UPDATED_MATURITY_DATE)
            .interestTenure(UPDATED_INTEREST_TENURE)
            .maturityAmount(UPDATED_MATURITY_AMOUNT);

        restFixedDepositMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFixedDeposit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFixedDeposit))
            )
            .andExpect(status().isOk());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeUpdate);
        FixedDeposit testFixedDeposit = fixedDepositList.get(fixedDepositList.size() - 1);
        assertThat(testFixedDeposit.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFixedDeposit.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testFixedDeposit.getLastRenewDate()).isEqualTo(UPDATED_LAST_RENEW_DATE);
        assertThat(testFixedDeposit.getMaturityDate()).isEqualTo(UPDATED_MATURITY_DATE);
        assertThat(testFixedDeposit.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testFixedDeposit.getTaxRate()).isEqualTo(DEFAULT_TAX_RATE);
        assertThat(testFixedDeposit.getTenure()).isEqualTo(DEFAULT_TENURE);
        assertThat(testFixedDeposit.getInterestTenure()).isEqualTo(UPDATED_INTEREST_TENURE);
        assertThat(testFixedDeposit.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testFixedDeposit.getMaturityAmount()).isEqualByComparingTo(UPDATED_MATURITY_AMOUNT);
        assertThat(testFixedDeposit.getTax()).isEqualByComparingTo(DEFAULT_TAX);
        assertThat(testFixedDeposit.getMonthlyDeposit()).isEqualByComparingTo(DEFAULT_MONTHLY_DEPOSIT);
        assertThat(testFixedDeposit.getMonthlyDepositDate()).isEqualTo(DEFAULT_MONTHLY_DEPOSIT_DATE);
        assertThat(testFixedDeposit.getBank()).isEqualTo(DEFAULT_BANK);
        assertThat(testFixedDeposit.getAutoRenew()).isEqualTo(DEFAULT_AUTO_RENEW);
        assertThat(testFixedDeposit.getRenewWithInterest()).isEqualTo(DEFAULT_RENEW_WITH_INTEREST);
        assertThat(testFixedDeposit.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateFixedDepositWithPatch() throws Exception {
        // Initialize the database
        fixedDeposit.setId(UUID.randomUUID().toString());
        fixedDepositRepository.saveAndFlush(fixedDeposit);

        int databaseSizeBeforeUpdate = fixedDepositRepository.findAll().size();

        // Update the fixedDeposit using partial update
        FixedDeposit partialUpdatedFixedDeposit = new FixedDeposit();
        partialUpdatedFixedDeposit.setId(fixedDeposit.getId());

        partialUpdatedFixedDeposit
            .type(UPDATED_TYPE)
            .issueDate(UPDATED_ISSUE_DATE)
            .lastRenewDate(UPDATED_LAST_RENEW_DATE)
            .maturityDate(UPDATED_MATURITY_DATE)
            .interestRate(UPDATED_INTEREST_RATE)
            .taxRate(UPDATED_TAX_RATE)
            .tenure(UPDATED_TENURE)
            .interestTenure(UPDATED_INTEREST_TENURE)
            .amount(UPDATED_AMOUNT)
            .maturityAmount(UPDATED_MATURITY_AMOUNT)
            .tax(UPDATED_TAX)
            .monthlyDeposit(UPDATED_MONTHLY_DEPOSIT)
            .monthlyDepositDate(UPDATED_MONTHLY_DEPOSIT_DATE)
            .bank(UPDATED_BANK)
            .autoRenew(UPDATED_AUTO_RENEW)
            .renewWithInterest(UPDATED_RENEW_WITH_INTEREST)
            .status(UPDATED_STATUS);

        restFixedDepositMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFixedDeposit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFixedDeposit))
            )
            .andExpect(status().isOk());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeUpdate);
        FixedDeposit testFixedDeposit = fixedDepositList.get(fixedDepositList.size() - 1);
        assertThat(testFixedDeposit.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFixedDeposit.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testFixedDeposit.getLastRenewDate()).isEqualTo(UPDATED_LAST_RENEW_DATE);
        assertThat(testFixedDeposit.getMaturityDate()).isEqualTo(UPDATED_MATURITY_DATE);
        assertThat(testFixedDeposit.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testFixedDeposit.getTaxRate()).isEqualTo(UPDATED_TAX_RATE);
        assertThat(testFixedDeposit.getTenure()).isEqualTo(UPDATED_TENURE);
        assertThat(testFixedDeposit.getInterestTenure()).isEqualTo(UPDATED_INTEREST_TENURE);
        assertThat(testFixedDeposit.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testFixedDeposit.getMaturityAmount()).isEqualByComparingTo(UPDATED_MATURITY_AMOUNT);
        assertThat(testFixedDeposit.getTax()).isEqualByComparingTo(UPDATED_TAX);
        assertThat(testFixedDeposit.getMonthlyDeposit()).isEqualByComparingTo(UPDATED_MONTHLY_DEPOSIT);
        assertThat(testFixedDeposit.getMonthlyDepositDate()).isEqualTo(UPDATED_MONTHLY_DEPOSIT_DATE);
        assertThat(testFixedDeposit.getBank()).isEqualTo(UPDATED_BANK);
        assertThat(testFixedDeposit.getAutoRenew()).isEqualTo(UPDATED_AUTO_RENEW);
        assertThat(testFixedDeposit.getRenewWithInterest()).isEqualTo(UPDATED_RENEW_WITH_INTEREST);
        assertThat(testFixedDeposit.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingFixedDeposit() throws Exception {
        int databaseSizeBeforeUpdate = fixedDepositRepository.findAll().size();
        fixedDeposit.setId(UUID.randomUUID().toString());

        // Create the FixedDeposit
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFixedDepositMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fixedDepositDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFixedDeposit() throws Exception {
        int databaseSizeBeforeUpdate = fixedDepositRepository.findAll().size();
        fixedDeposit.setId(UUID.randomUUID().toString());

        // Create the FixedDeposit
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFixedDepositMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFixedDeposit() throws Exception {
        int databaseSizeBeforeUpdate = fixedDepositRepository.findAll().size();
        fixedDeposit.setId(UUID.randomUUID().toString());

        // Create the FixedDeposit
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFixedDepositMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFixedDeposit() throws Exception {
        // Initialize the database
        fixedDeposit.setId(UUID.randomUUID().toString());
        fixedDepositRepository.saveAndFlush(fixedDeposit);

        int databaseSizeBeforeDelete = fixedDepositRepository.findAll().size();

        // Delete the fixedDeposit
        restFixedDepositMockMvc
            .perform(delete(ENTITY_API_URL_ID, fixedDeposit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
