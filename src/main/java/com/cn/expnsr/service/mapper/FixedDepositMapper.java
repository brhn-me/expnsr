package com.cn.expnsr.service.mapper;

import com.cn.expnsr.domain.FixedDeposit;
import com.cn.expnsr.service.dto.FixedDepositDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FixedDeposit} and its DTO {@link FixedDepositDTO}.
 */
@Mapper(componentModel = "spring")
public interface FixedDepositMapper extends EntityMapper<FixedDepositDTO, FixedDeposit> {}
