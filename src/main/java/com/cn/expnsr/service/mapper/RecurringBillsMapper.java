package com.cn.expnsr.service.mapper;

import com.cn.expnsr.domain.RecurringBills;
import com.cn.expnsr.service.dto.RecurringBillsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RecurringBills} and its DTO {@link RecurringBillsDTO}.
 */
@Mapper(componentModel = "spring")
public interface RecurringBillsMapper extends EntityMapper<RecurringBillsDTO, RecurringBills> {}
