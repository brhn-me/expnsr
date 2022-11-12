package com.cn.expnsr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cn.expnsr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FixedDepositDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedDepositDTO.class);
        FixedDepositDTO fixedDepositDTO1 = new FixedDepositDTO();
        fixedDepositDTO1.setId("id1");
        FixedDepositDTO fixedDepositDTO2 = new FixedDepositDTO();
        assertThat(fixedDepositDTO1).isNotEqualTo(fixedDepositDTO2);
        fixedDepositDTO2.setId(fixedDepositDTO1.getId());
        assertThat(fixedDepositDTO1).isEqualTo(fixedDepositDTO2);
        fixedDepositDTO2.setId("id2");
        assertThat(fixedDepositDTO1).isNotEqualTo(fixedDepositDTO2);
        fixedDepositDTO1.setId(null);
        assertThat(fixedDepositDTO1).isNotEqualTo(fixedDepositDTO2);
    }
}
