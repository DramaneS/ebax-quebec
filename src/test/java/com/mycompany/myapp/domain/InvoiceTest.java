package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.InvoiceTestSamples.*;
import static com.mycompany.myapp.domain.ServiceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InvoiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invoice.class);
        Invoice invoice1 = getInvoiceSample1();
        Invoice invoice2 = new Invoice();
        assertThat(invoice1).isNotEqualTo(invoice2);

        invoice2.setId(invoice1.getId());
        assertThat(invoice1).isEqualTo(invoice2);

        invoice2 = getInvoiceSample2();
        assertThat(invoice1).isNotEqualTo(invoice2);
    }

    @Test
    void serviceTest() throws Exception {
        Invoice invoice = getInvoiceRandomSampleGenerator();
        Service serviceBack = getServiceRandomSampleGenerator();

        invoice.setService(serviceBack);
        assertThat(invoice.getService()).isEqualTo(serviceBack);

        invoice.service(null);
        assertThat(invoice.getService()).isNull();
    }
}
