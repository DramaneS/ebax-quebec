package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CustumerTestSamples.*;
import static com.mycompany.myapp.domain.ServiceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustumerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Custumer.class);
        Custumer custumer1 = getCustumerSample1();
        Custumer custumer2 = new Custumer();
        assertThat(custumer1).isNotEqualTo(custumer2);

        custumer2.setId(custumer1.getId());
        assertThat(custumer1).isEqualTo(custumer2);

        custumer2 = getCustumerSample2();
        assertThat(custumer1).isNotEqualTo(custumer2);
    }

    @Test
    void serviceTest() throws Exception {
        Custumer custumer = getCustumerRandomSampleGenerator();
        Service serviceBack = getServiceRandomSampleGenerator();

        custumer.setService(serviceBack);
        assertThat(custumer.getService()).isEqualTo(serviceBack);

        custumer.service(null);
        assertThat(custumer.getService()).isNull();
    }
}
