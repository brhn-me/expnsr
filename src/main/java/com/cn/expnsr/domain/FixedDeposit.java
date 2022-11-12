package com.cn.expnsr.domain;

import com.cn.expnsr.domain.enumeration.FDStatus;
import com.cn.expnsr.domain.enumeration.FDType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A FixedDeposit.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "fixed_deposit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FixedDeposit implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private FDType type;

    @NotNull
    @Column(name = "issue_date", nullable = false)
    private Instant issueDate;

    @Column(name = "last_renew_date")
    private Instant lastRenewDate;

    @NotNull
    @Column(name = "maturity_date", nullable = false)
    private Instant maturityDate;

    @NotNull
    @Column(name = "interest_rate", nullable = false)
    private Float interestRate;

    @NotNull
    @Column(name = "tax_rate", nullable = false)
    private Float taxRate;

    @NotNull
    @Column(name = "tenure", nullable = false)
    private Integer tenure;

    @NotNull
    @Column(name = "interest_tenure", nullable = false)
    private Integer interestTenure;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "maturity_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal maturityAmount;

    @NotNull
    @Column(name = "tax", precision = 21, scale = 2, nullable = false)
    private BigDecimal tax;

    @NotNull
    @Column(name = "monthly_deposit", precision = 21, scale = 2, nullable = false)
    private BigDecimal monthlyDeposit;

    @Column(name = "monthly_deposit_date")
    private Instant monthlyDepositDate;

    @NotNull
    @Column(name = "bank", nullable = false)
    private String bank;

    @NotNull
    @Column(name = "auto_renew", nullable = false)
    private Boolean autoRenew;

    @NotNull
    @Column(name = "renew_with_interest", nullable = false)
    private Boolean renewWithInterest;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FDStatus status;

    @Transient
    private boolean isPersisted;

    @OneToMany(mappedBy = "fixedDeposit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fixedDeposit" }, allowSetters = true)
    private Set<Interest> interests = new HashSet<>();

    @OneToMany(mappedBy = "fixedDeposit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fixedDeposit" }, allowSetters = true)
    private Set<Deposit> deposits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public FixedDeposit id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FDType getType() {
        return this.type;
    }

    public FixedDeposit type(FDType type) {
        this.setType(type);
        return this;
    }

    public void setType(FDType type) {
        this.type = type;
    }

    public Instant getIssueDate() {
        return this.issueDate;
    }

    public FixedDeposit issueDate(Instant issueDate) {
        this.setIssueDate(issueDate);
        return this;
    }

    public void setIssueDate(Instant issueDate) {
        this.issueDate = issueDate;
    }

    public Instant getLastRenewDate() {
        return this.lastRenewDate;
    }

    public FixedDeposit lastRenewDate(Instant lastRenewDate) {
        this.setLastRenewDate(lastRenewDate);
        return this;
    }

    public void setLastRenewDate(Instant lastRenewDate) {
        this.lastRenewDate = lastRenewDate;
    }

    public Instant getMaturityDate() {
        return this.maturityDate;
    }

    public FixedDeposit maturityDate(Instant maturityDate) {
        this.setMaturityDate(maturityDate);
        return this;
    }

    public void setMaturityDate(Instant maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Float getInterestRate() {
        return this.interestRate;
    }

    public FixedDeposit interestRate(Float interestRate) {
        this.setInterestRate(interestRate);
        return this;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public Float getTaxRate() {
        return this.taxRate;
    }

    public FixedDeposit taxRate(Float taxRate) {
        this.setTaxRate(taxRate);
        return this;
    }

    public void setTaxRate(Float taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getTenure() {
        return this.tenure;
    }

    public FixedDeposit tenure(Integer tenure) {
        this.setTenure(tenure);
        return this;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public Integer getInterestTenure() {
        return this.interestTenure;
    }

    public FixedDeposit interestTenure(Integer interestTenure) {
        this.setInterestTenure(interestTenure);
        return this;
    }

    public void setInterestTenure(Integer interestTenure) {
        this.interestTenure = interestTenure;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public FixedDeposit amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getMaturityAmount() {
        return this.maturityAmount;
    }

    public FixedDeposit maturityAmount(BigDecimal maturityAmount) {
        this.setMaturityAmount(maturityAmount);
        return this;
    }

    public void setMaturityAmount(BigDecimal maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    public BigDecimal getTax() {
        return this.tax;
    }

    public FixedDeposit tax(BigDecimal tax) {
        this.setTax(tax);
        return this;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getMonthlyDeposit() {
        return this.monthlyDeposit;
    }

    public FixedDeposit monthlyDeposit(BigDecimal monthlyDeposit) {
        this.setMonthlyDeposit(monthlyDeposit);
        return this;
    }

    public void setMonthlyDeposit(BigDecimal monthlyDeposit) {
        this.monthlyDeposit = monthlyDeposit;
    }

    public Instant getMonthlyDepositDate() {
        return this.monthlyDepositDate;
    }

    public FixedDeposit monthlyDepositDate(Instant monthlyDepositDate) {
        this.setMonthlyDepositDate(monthlyDepositDate);
        return this;
    }

    public void setMonthlyDepositDate(Instant monthlyDepositDate) {
        this.monthlyDepositDate = monthlyDepositDate;
    }

    public String getBank() {
        return this.bank;
    }

    public FixedDeposit bank(String bank) {
        this.setBank(bank);
        return this;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Boolean getAutoRenew() {
        return this.autoRenew;
    }

    public FixedDeposit autoRenew(Boolean autoRenew) {
        this.setAutoRenew(autoRenew);
        return this;
    }

    public void setAutoRenew(Boolean autoRenew) {
        this.autoRenew = autoRenew;
    }

    public Boolean getRenewWithInterest() {
        return this.renewWithInterest;
    }

    public FixedDeposit renewWithInterest(Boolean renewWithInterest) {
        this.setRenewWithInterest(renewWithInterest);
        return this;
    }

    public void setRenewWithInterest(Boolean renewWithInterest) {
        this.renewWithInterest = renewWithInterest;
    }

    public FDStatus getStatus() {
        return this.status;
    }

    public FixedDeposit status(FDStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(FDStatus status) {
        this.status = status;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public FixedDeposit setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public Set<Interest> getInterests() {
        return this.interests;
    }

    public void setInterests(Set<Interest> interests) {
        if (this.interests != null) {
            this.interests.forEach(i -> i.setFixedDeposit(null));
        }
        if (interests != null) {
            interests.forEach(i -> i.setFixedDeposit(this));
        }
        this.interests = interests;
    }

    public FixedDeposit interests(Set<Interest> interests) {
        this.setInterests(interests);
        return this;
    }

    public FixedDeposit addInterest(Interest interest) {
        this.interests.add(interest);
        interest.setFixedDeposit(this);
        return this;
    }

    public FixedDeposit removeInterest(Interest interest) {
        this.interests.remove(interest);
        interest.setFixedDeposit(null);
        return this;
    }

    public Set<Deposit> getDeposits() {
        return this.deposits;
    }

    public void setDeposits(Set<Deposit> deposits) {
        if (this.deposits != null) {
            this.deposits.forEach(i -> i.setFixedDeposit(null));
        }
        if (deposits != null) {
            deposits.forEach(i -> i.setFixedDeposit(this));
        }
        this.deposits = deposits;
    }

    public FixedDeposit deposits(Set<Deposit> deposits) {
        this.setDeposits(deposits);
        return this;
    }

    public FixedDeposit addDeposit(Deposit deposit) {
        this.deposits.add(deposit);
        deposit.setFixedDeposit(this);
        return this;
    }

    public FixedDeposit removeDeposit(Deposit deposit) {
        this.deposits.remove(deposit);
        deposit.setFixedDeposit(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FixedDeposit)) {
            return false;
        }
        return id != null && id.equals(((FixedDeposit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FixedDeposit{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", lastRenewDate='" + getLastRenewDate() + "'" +
            ", maturityDate='" + getMaturityDate() + "'" +
            ", interestRate=" + getInterestRate() +
            ", taxRate=" + getTaxRate() +
            ", tenure=" + getTenure() +
            ", interestTenure=" + getInterestTenure() +
            ", amount=" + getAmount() +
            ", maturityAmount=" + getMaturityAmount() +
            ", tax=" + getTax() +
            ", monthlyDeposit=" + getMonthlyDeposit() +
            ", monthlyDepositDate='" + getMonthlyDepositDate() + "'" +
            ", bank='" + getBank() + "'" +
            ", autoRenew='" + getAutoRenew() + "'" +
            ", renewWithInterest='" + getRenewWithInterest() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
