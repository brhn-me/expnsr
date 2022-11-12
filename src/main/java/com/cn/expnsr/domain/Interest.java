package com.cn.expnsr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Interest.
 */
@Entity
@Table(name = "interest")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Interest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Column(name = "interest_rate", nullable = false)
    private Float interestRate;

    @NotNull
    @Column(name = "tax_rate", nullable = false)
    private Float taxRate;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "tax", precision = 21, scale = 2, nullable = false)
    private BigDecimal tax;

    @Column(name = "verified")
    private Boolean verified;

    @ManyToOne
    @JsonIgnoreProperties(value = { "interests", "deposits" }, allowSetters = true)
    private FixedDeposit fixedDeposit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Interest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public Interest date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Float getInterestRate() {
        return this.interestRate;
    }

    public Interest interestRate(Float interestRate) {
        this.setInterestRate(interestRate);
        return this;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public Float getTaxRate() {
        return this.taxRate;
    }

    public Interest taxRate(Float taxRate) {
        this.setTaxRate(taxRate);
        return this;
    }

    public void setTaxRate(Float taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Interest amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTax() {
        return this.tax;
    }

    public Interest tax(BigDecimal tax) {
        this.setTax(tax);
        return this;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Boolean getVerified() {
        return this.verified;
    }

    public Interest verified(Boolean verified) {
        this.setVerified(verified);
        return this;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public FixedDeposit getFixedDeposit() {
        return this.fixedDeposit;
    }

    public void setFixedDeposit(FixedDeposit fixedDeposit) {
        this.fixedDeposit = fixedDeposit;
    }

    public Interest fixedDeposit(FixedDeposit fixedDeposit) {
        this.setFixedDeposit(fixedDeposit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Interest)) {
            return false;
        }
        return id != null && id.equals(((Interest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Interest{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", interestRate=" + getInterestRate() +
            ", taxRate=" + getTaxRate() +
            ", amount=" + getAmount() +
            ", tax=" + getTax() +
            ", verified='" + getVerified() + "'" +
            "}";
    }
}
