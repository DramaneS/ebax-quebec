package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EntrepriseTestSamples.*;
import static com.mycompany.myapp.domain.InvoiceTestSamples.*;
import static com.mycompany.myapp.domain.ProjectTestSamples.*;
import static com.mycompany.myapp.domain.ServiceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Service.class);
        Service service1 = getServiceSample1();
        Service service2 = new Service();
        assertThat(service1).isNotEqualTo(service2);

        service2.setId(service1.getId());
        assertThat(service1).isEqualTo(service2);

        service2 = getServiceSample2();
        assertThat(service1).isNotEqualTo(service2);
    }

    @Test
    void entrepriseTest() throws Exception {
        Service service = getServiceRandomSampleGenerator();
        Entreprise entrepriseBack = getEntrepriseRandomSampleGenerator();

        service.setEntreprise(entrepriseBack);
        assertThat(service.getEntreprise()).isEqualTo(entrepriseBack);

        service.entreprise(null);
        assertThat(service.getEntreprise()).isNull();
    }

    @Test
    void projectTest() throws Exception {
        Service service = getServiceRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        service.addProject(projectBack);
        assertThat(service.getProjects()).containsOnly(projectBack);

        service.removeProject(projectBack);
        assertThat(service.getProjects()).doesNotContain(projectBack);

        service.projects(new HashSet<>(Set.of(projectBack)));
        assertThat(service.getProjects()).containsOnly(projectBack);

        service.setProjects(new HashSet<>());
        assertThat(service.getProjects()).doesNotContain(projectBack);
    }

    @Test
    void invoiceTest() throws Exception {
        Service service = getServiceRandomSampleGenerator();
        Invoice invoiceBack = getInvoiceRandomSampleGenerator();

        service.setInvoice(invoiceBack);
        assertThat(service.getInvoice()).isEqualTo(invoiceBack);
        assertThat(invoiceBack.getService()).isEqualTo(service);

        service.invoice(null);
        assertThat(service.getInvoice()).isNull();
        assertThat(invoiceBack.getService()).isNull();
    }
}
