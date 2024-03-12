package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.HydroQuebecRateTestSamples.*;
import static com.mycompany.myapp.domain.ProjectTestSamples.*;
import static com.mycompany.myapp.domain.ServiceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Project.class);
        Project project1 = getProjectSample1();
        Project project2 = new Project();
        assertThat(project1).isNotEqualTo(project2);

        project2.setId(project1.getId());
        assertThat(project1).isEqualTo(project2);

        project2 = getProjectSample2();
        assertThat(project1).isNotEqualTo(project2);
    }

    @Test
    void hydroQuebecRateTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        HydroQuebecRate hydroQuebecRateBack = getHydroQuebecRateRandomSampleGenerator();

        project.setHydroQuebecRate(hydroQuebecRateBack);
        assertThat(project.getHydroQuebecRate()).isEqualTo(hydroQuebecRateBack);

        project.hydroQuebecRate(null);
        assertThat(project.getHydroQuebecRate()).isNull();
    }

    @Test
    void serviceTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        Service serviceBack = getServiceRandomSampleGenerator();

        project.addService(serviceBack);
        assertThat(project.getServices()).containsOnly(serviceBack);
        assertThat(serviceBack.getProjects()).containsOnly(project);

        project.removeService(serviceBack);
        assertThat(project.getServices()).doesNotContain(serviceBack);
        assertThat(serviceBack.getProjects()).doesNotContain(project);

        project.services(new HashSet<>(Set.of(serviceBack)));
        assertThat(project.getServices()).containsOnly(serviceBack);
        assertThat(serviceBack.getProjects()).containsOnly(project);

        project.setServices(new HashSet<>());
        assertThat(project.getServices()).doesNotContain(serviceBack);
        assertThat(serviceBack.getProjects()).doesNotContain(project);
    }
}
