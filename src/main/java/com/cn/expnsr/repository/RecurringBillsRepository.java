package com.cn.expnsr.repository;

import com.cn.expnsr.domain.RecurringBills;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RecurringBills entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecurringBillsRepository extends JpaRepository<RecurringBills, Long> {}
