package com.cn.expnsr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cn.expnsr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepositDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepositDTO.class);
        DepositDTO depositDTO1 = new DepositDTO();
        depositDTO1.setId(1L);
        DepositDTO depositDTO2 = new DepositDTO();
        assertThat(depositDTO1).isNotEqualTo(depositDTO2);
        depositDTO2.setId(depositDTO1.getId());
        assertThat(depositDTO1).isEqualTo(depositDTO2);
        depositDTO2.setId(2L);
        assertThat(depositDTO1).isNotEqualTo(depositDTO2);
        depositDTO1.setId(null);
        assertThat(depositDTO1).isNotEqualTo(depositDTO2);
    }
}
