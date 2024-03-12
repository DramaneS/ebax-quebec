package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EntrepriseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntrepriseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entreprise.class);
        Entreprise entreprise1 = getEntrepriseSample1();
        Entreprise entreprise2 = new Entreprise();
        assertThat(entreprise1).isNotEqualTo(entreprise2);

        entreprise2.setId(entreprise1.getId());
        assertThat(entreprise1).isEqualTo(entreprise2);

        entreprise2 = getEntrepriseSample2();
        assertThat(entreprise1).isNotEqualTo(entreprise2);
    }
}
