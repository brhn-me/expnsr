package com.cn.expnsr.service.mapper;

import com.cn.expnsr.domain.Transaction;
import com.cn.expnsr.service.dto.TransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {}
