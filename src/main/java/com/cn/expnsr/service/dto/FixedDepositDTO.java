package com.cn.expnsr.service.dto;

import com.cn.expnsr.domain.enumeration.FDStatus;
import com.cn.expnsr.domain.enumeration.FDType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.cn.expnsr.domain.FixedDeposit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FixedDepositDTO implements Serializable {

    @NotNull
    private String id;

    @NotNull
    private FDType type;

    @NotNull
    private Instant issueDate;

    private Instant lastRenewDate;

    @NotNull
    private Instant maturityDate;

    @NotNull
    private Float interestRate;

    @NotNull
    private Float taxRate;

    @NotNull
    private Integer tenure;

    @NotNull
    private Integer interestTenure;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private BigDecimal maturityAmount;

    @NotNull
    private BigDecimal tax;

    @NotNull
    private BigDecimal monthlyDeposit;

    private Instant monthlyDepositDate;

    @NotNull
    private String bank;

    @NotNull
    private Boolean autoRenew;

    @NotNull
    private Boolean renewWithInterest;

    @NotNull
    private FDStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FDType getType() {
        return type;
    }

    public void setType(FDType type) {
        this.type = type;
    }

    public Instant getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Instant issueDate) {
        this.issueDate = issueDate;
    }

    public Instant getLastRenewDate() {
        return lastRenewDate;
    }

    public void setLastRenewDate(Instant lastRenewDate) {
        this.lastRenewDate = lastRenewDate;
    }

    public Instant getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Instant maturityDate) {
        this.maturityDate = maturityDate;
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

    public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public Integer getInterestTenure() {
        return interestTenure;
    }

    public void setInterestTenure(Integer interestTenure) {
        this.interestTenure = interestTenure;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(BigDecimal maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getMonthlyDeposit() {
        return monthlyDeposit;
    }

    public void setMonthlyDeposit(BigDecimal monthlyDeposit) {
        this.monthlyDeposit = monthlyDeposit;
    }

    public Instant getMonthlyDepositDate() {
        return monthlyDepositDate;
    }

    public void setMonthlyDepositDate(Instant monthlyDepositDate) {
        this.monthlyDepositDate = monthlyDepositDate;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Boolean getAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(Boolean autoRenew) {
        this.autoRenew = autoRenew;
    }

    public Boolean getRenewWithInterest() {
        return renewWithInterest;
    }

    public void setRenewWithInterest(Boolean renewWithInterest) {
        this.renewWithInterest = renewWithInterest;
    }

    public FDStatus getStatus() {
        return status;
    }

    public void setStatus(FDStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FixedDepositDTO)) {
            return false;
        }

        FixedDepositDTO fixedDepositDTO = (FixedDepositDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fixedDepositDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FixedDepositDTO{" +
            "id='" + getId() + "'" +
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
