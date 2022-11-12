package com.cn.expnsr.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.cn.expnsr.domain.RecurringBills} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecurringBillsDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Integer tenure;

    @NotNull
    private String primaryCategory;

    private String secondaryCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public String getPrimaryCategory() {
        return primaryCategory;
    }

    public void setPrimaryCategory(String primaryCategory) {
        this.primaryCategory = primaryCategory;
    }

    public String getSecondaryCategory() {
        return secondaryCategory;
    }

    public void setSecondaryCategory(String secondaryCategory) {
        this.secondaryCategory = secondaryCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecurringBillsDTO)) {
            return false;
        }

        RecurringBillsDTO recurringBillsDTO = (RecurringBillsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recurringBillsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecurringBillsDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", tenure=" + getTenure() +
            ", primaryCategory='" + getPrimaryCategory() + "'" +
            ", secondaryCategory='" + getSecondaryCategory() + "'" +
            "}";
    }
}
