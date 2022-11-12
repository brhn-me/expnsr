package com.cn.expnsr.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.cn.expnsr.domain.Deposit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepositDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant date;

    @NotNull
    private BigDecimal amount;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
        if (!(o instanceof DepositDTO)) {
            return false;
        }

        DepositDTO depositDTO = (DepositDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, depositDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepositDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", verified='" + getVerified() + "'" +
            ", fixedDeposit=" + getFixedDeposit() +
            "}";
    }
}
