package com.cn.expnsr.domain;

import com.cn.expnsr.domain.enumeration.TransactionType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @NotNull
    @Column(name = "primary_category", nullable = false)
    private String primaryCategory;

    @Column(name = "secondary_category")
    private String secondaryCategory;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "due", precision = 21, scale = 2)
    private BigDecimal due;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "tags")
    private String tags;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Transaction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public Transaction date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public TransactionType getType() {
        return this.type;
    }

    public Transaction type(TransactionType type) {
        this.setType(type);
        return this;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getPrimaryCategory() {
        return this.primaryCategory;
    }

    public Transaction primaryCategory(String primaryCategory) {
        this.setPrimaryCategory(primaryCategory);
        return this;
    }

    public void setPrimaryCategory(String primaryCategory) {
        this.primaryCategory = primaryCategory;
    }

    public String getSecondaryCategory() {
        return this.secondaryCategory;
    }

    public Transaction secondaryCategory(String secondaryCategory) {
        this.setSecondaryCategory(secondaryCategory);
        return this;
    }

    public void setSecondaryCategory(String secondaryCategory) {
        this.secondaryCategory = secondaryCategory;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Transaction amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDue() {
        return this.due;
    }

    public Transaction due(BigDecimal due) {
        this.setDue(due);
        return this;
    }

    public void setDue(BigDecimal due) {
        this.due = due;
    }

    public String getTitle() {
        return this.title;
    }

    public Transaction title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Transaction description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return this.tags;
    }

    public Transaction tags(String tags) {
        this.setTags(tags);
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            ", primaryCategory='" + getPrimaryCategory() + "'" +
            ", secondaryCategory='" + getSecondaryCategory() + "'" +
            ", amount=" + getAmount() +
            ", due=" + getDue() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", tags='" + getTags() + "'" +
            "}";
    }
}
