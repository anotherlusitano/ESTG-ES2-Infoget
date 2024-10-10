package com.es2.infoget.domain;

import static com.es2.infoget.domain.DisciplinasTestSamples.*;
import static com.es2.infoget.domain.ProfessoresTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.es2.infoget.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProfessoresTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Professores.class);
        Professores professores1 = getProfessoresSample1();
        Professores professores2 = new Professores();
        assertThat(professores1).isNotEqualTo(professores2);

        professores2.setId(professores1.getId());
        assertThat(professores1).isEqualTo(professores2);

        professores2 = getProfessoresSample2();
        assertThat(professores1).isNotEqualTo(professores2);
    }

    @Test
    void disciplinasTest() {
        Professores professores = getProfessoresRandomSampleGenerator();
        Disciplinas disciplinasBack = getDisciplinasRandomSampleGenerator();

        professores.addDisciplinas(disciplinasBack);
        assertThat(professores.getDisciplinas()).containsOnly(disciplinasBack);
        assertThat(disciplinasBack.getProfessores()).isEqualTo(professores);

        professores.removeDisciplinas(disciplinasBack);
        assertThat(professores.getDisciplinas()).doesNotContain(disciplinasBack);
        assertThat(disciplinasBack.getProfessores()).isNull();

        professores.disciplinas(new HashSet<>(Set.of(disciplinasBack)));
        assertThat(professores.getDisciplinas()).containsOnly(disciplinasBack);
        assertThat(disciplinasBack.getProfessores()).isEqualTo(professores);

        professores.setDisciplinas(new HashSet<>());
        assertThat(professores.getDisciplinas()).doesNotContain(disciplinasBack);
        assertThat(disciplinasBack.getProfessores()).isNull();
    }
}
