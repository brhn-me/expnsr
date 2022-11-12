package com.cn.expnsr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cn.expnsr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepositTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deposit.class);
        Deposit deposit1 = new Deposit();
        deposit1.setId(1L);
        Deposit deposit2 = new Deposit();
        deposit2.setId(deposit1.getId());
        assertThat(deposit1).isEqualTo(deposit2);
        deposit2.setId(2L);
        assertThat(deposit1).isNotEqualTo(deposit2);
        deposit1.setId(null);
        assertThat(deposit1).isNotEqualTo(deposit2);
    }
}
