package com.cn.expnsr.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.cn.expnsr.domain.Interest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InterestDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant date;

    @NotNull
    private Float interestRate;

    @NotNull
    private Float taxRate;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private BigDecimal tax;

    private Boolean verified;

    private FixedDepositDTO fixedDeposit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public Float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Float taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public FixedDepositDTO getFixedDeposit() {
        return fixedDeposit;
    }

    public void setFixedDeposit(FixedDepositDTO fixedDeposit) {
        this.fixedDeposit = fixedDeposit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterestDTO)) {
            return false;
        }

        InterestDTO interestDTO = (InterestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, interestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterestDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", interestRate=" + getInterestRate() +
            ", taxRate=" + getTaxRate() +
            ", amount=" + getAmount() +
            ", tax=" + getTax() +
            ", verified='" + getVerified() + "'" +
            ", fixedDeposit=" + getFixedDeposit() +
            "}";
    }
}
