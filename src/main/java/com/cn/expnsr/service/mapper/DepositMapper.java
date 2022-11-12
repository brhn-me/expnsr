package com.cn.expnsr.service.mapper;

import com.cn.expnsr.domain.Deposit;
import com.cn.expnsr.domain.FixedDeposit;
import com.cn.expnsr.service.dto.DepositDTO;
import com.cn.expnsr.service.dto.FixedDepositDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Deposit} and its DTO {@link DepositDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepositMapper extends EntityMapper<DepositDTO, Deposit> {
    @Mapping(target = "fixedDeposit", source = "fixedDeposit", qualifiedByName = "fixedDepositId")
    DepositDTO toDto(Deposit s);

    @Named("fixedDepositId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FixedDepositDTO toDtoFixedDepositId(FixedDeposit fixedDeposit);
}
