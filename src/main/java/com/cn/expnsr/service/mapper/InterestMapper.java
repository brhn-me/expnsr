package com.cn.expnsr.service.mapper;

import com.cn.expnsr.domain.FixedDeposit;
import com.cn.expnsr.domain.Interest;
import com.cn.expnsr.service.dto.FixedDepositDTO;
import com.cn.expnsr.service.dto.InterestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Interest} and its DTO {@link InterestDTO}.
 */
@Mapper(componentModel = "spring")
public interface InterestMapper extends EntityMapper<InterestDTO, Interest> {
    @Mapping(target = "fixedDeposit", source = "fixedDeposit", qualifiedByName = "fixedDepositId")
    InterestDTO toDto(Interest s);

    @Named("fixedDepositId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FixedDepositDTO toDtoFixedDepositId(FixedDeposit fixedDeposit);
}
