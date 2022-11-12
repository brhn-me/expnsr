package com.cn.expnsr.repository;

import com.cn.expnsr.domain.Deposit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Deposit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {}
