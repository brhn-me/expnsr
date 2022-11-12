package com.cn.expnsr.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecurringBillsMapperTest {

    private RecurringBillsMapper recurringBillsMapper;

    @BeforeEach
    public void setUp() {
        recurringBillsMapper = new RecurringBillsMapperImpl();
    }
}
