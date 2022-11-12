package com.cn.expnsr.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RecurringBills.
 */
@Entity
@Table(name = "recurring_bills")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecurringBills implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "tenure", nullable = false)
    private Integer tenure;

    @NotNull
    @Column(name = "primary_category", nullable = false)
    private String primaryCategory;

    @Column(name = "secondary_category")
    private String secondaryCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RecurringBills id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public RecurringBills amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTenure() {
        return this.tenure;
    }

    public RecurringBills tenure(Integer tenure) {
        this.setTenure(tenure);
        return this;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public String getPrimaryCategory() {
        return this.primaryCategory;
    }

    public RecurringBills primaryCategory(String primaryCategory) {
        this.setPrimaryCategory(primaryCategory);
        return this;
    }

    public void setPrimaryCategory(String primaryCategory) {
        this.primaryCategory = primaryCategory;
    }

    public String getSecondaryCategory() {
        return this.secondaryCategory;
    }

    public RecurringBills secondaryCategory(String secondaryCategory) {
        this.setSecondaryCategory(secondaryCategory);
        return this;
    }

    public void setSecondaryCategory(String secondaryCategory) {
        this.secondaryCategory = secondaryCategory;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecurringBills)) {
            return false;
        }
        return id != null && id.equals(((RecurringBills) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecurringBills{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", tenure=" + getTenure() +
            ", primaryCategory='" + getPrimaryCategory() + "'" +
            ", secondaryCategory='" + getSecondaryCategory() + "'" +
            "}";
    }
}
