package com.cn.expnsr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cn.expnsr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecurringBillsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecurringBillsDTO.class);
        RecurringBillsDTO recurringBillsDTO1 = new RecurringBillsDTO();
        recurringBillsDTO1.setId(1L);
        RecurringBillsDTO recurringBillsDTO2 = new RecurringBillsDTO();
        assertThat(recurringBillsDTO1).isNotEqualTo(recurringBillsDTO2);
        recurringBillsDTO2.setId(recurringBillsDTO1.getId());
        assertThat(recurringBillsDTO1).isEqualTo(recurringBillsDTO2);
        recurringBillsDTO2.setId(2L);
        assertThat(recurringBillsDTO1).isNotEqualTo(recurringBillsDTO2);
        recurringBillsDTO1.setId(null);
        assertThat(recurringBillsDTO1).isNotEqualTo(recurringBillsDTO2);
    }
}
