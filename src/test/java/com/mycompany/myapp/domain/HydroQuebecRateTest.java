package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.HydroQuebecRateTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HydroQuebecRateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HydroQuebecRate.class);
        HydroQuebecRate hydroQuebecRate1 = getHydroQuebecRateSample1();
        HydroQuebecRate hydroQuebecRate2 = new HydroQuebecRate();
        assertThat(hydroQuebecRate1).isNotEqualTo(hydroQuebecRate2);

        hydroQuebecRate2.setId(hydroQuebecRate1.getId());
        assertThat(hydroQuebecRate1).isEqualTo(hydroQuebecRate2);

        hydroQuebecRate2 = getHydroQuebecRateSample2();
        assertThat(hydroQuebecRate1).isNotEqualTo(hydroQuebecRate2);
    }
}
