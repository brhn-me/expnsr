package com.cn.expnsr.repository;

import com.cn.expnsr.domain.FixedDeposit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FixedDeposit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FixedDepositRepository extends JpaRepository<FixedDeposit, String> {}
