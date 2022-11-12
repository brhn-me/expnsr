package com.cn.expnsr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cn.expnsr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FixedDepositTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedDeposit.class);
        FixedDeposit fixedDeposit1 = new FixedDeposit();
        fixedDeposit1.setId("id1");
        FixedDeposit fixedDeposit2 = new FixedDeposit();
        fixedDeposit2.setId(fixedDeposit1.getId());
        assertThat(fixedDeposit1).isEqualTo(fixedDeposit2);
        fixedDeposit2.setId("id2");
        assertThat(fixedDeposit1).isNotEqualTo(fixedDeposit2);
        fixedDeposit1.setId(null);
        assertThat(fixedDeposit1).isNotEqualTo(fixedDeposit2);
    }
}
