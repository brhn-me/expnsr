package com.cn.expnsr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cn.expnsr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecurringBillsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecurringBills.class);
        RecurringBills recurringBills1 = new RecurringBills();
        recurringBills1.setId(1L);
        RecurringBills recurringBills2 = new RecurringBills();
        recurringBills2.setId(recurringBills1.getId());
        assertThat(recurringBills1).isEqualTo(recurringBills2);
        recurringBills2.setId(2L);
        assertThat(recurringBills1).isNotEqualTo(recurringBills2);
        recurringBills1.setId(null);
        assertThat(recurringBills1).isNotEqualTo(recurringBills2);
    }
}
