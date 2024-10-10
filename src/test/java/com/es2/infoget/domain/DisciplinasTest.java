package com.es2.infoget.domain;

import static com.es2.infoget.domain.CursoDisciplinaTestSamples.*;
import static com.es2.infoget.domain.DisciplinasTestSamples.*;
import static com.es2.infoget.domain.NotasTestSamples.*;
import static com.es2.infoget.domain.ProfessoresTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.es2.infoget.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DisciplinasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Disciplinas.class);
        Disciplinas disciplinas1 = getDisciplinasSample1();
        Disciplinas disciplinas2 = new Disciplinas();
        assertThat(disciplinas1).isNotEqualTo(disciplinas2);

        disciplinas2.setId(disciplinas1.getId());
        assertThat(disciplinas1).isEqualTo(disciplinas2);

        disciplinas2 = getDisciplinasSample2();
        assertThat(disciplinas1).isNotEqualTo(disciplinas2);
    }

    @Test
    void cursoDisciplinaTest() {
        Disciplinas disciplinas = getDisciplinasRandomSampleGenerator();
        CursoDisciplina cursoDisciplinaBack = getCursoDisciplinaRandomSampleGenerator();

        disciplinas.addCursoDisciplina(cursoDisciplinaBack);
        assertThat(disciplinas.getCursoDisciplinas()).containsOnly(cursoDisciplinaBack);
        assertThat(cursoDisciplinaBack.getDisciplinas()).isEqualTo(disciplinas);

        disciplinas.removeCursoDisciplina(cursoDisciplinaBack);
        assertThat(disciplinas.getCursoDisciplinas()).doesNotContain(cursoDisciplinaBack);
        assertThat(cursoDisciplinaBack.getDisciplinas()).isNull();

        disciplinas.cursoDisciplinas(new HashSet<>(Set.of(cursoDisciplinaBack)));
        assertThat(disciplinas.getCursoDisciplinas()).containsOnly(cursoDisciplinaBack);
        assertThat(cursoDisciplinaBack.getDisciplinas()).isEqualTo(disciplinas);

        disciplinas.setCursoDisciplinas(new HashSet<>());
        assertThat(disciplinas.getCursoDisciplinas()).doesNotContain(cursoDisciplinaBack);
        assertThat(cursoDisciplinaBack.getDisciplinas()).isNull();
    }

    @Test
    void notasTest() {
        Disciplinas disciplinas = getDisciplinasRandomSampleGenerator();
        Notas notasBack = getNotasRandomSampleGenerator();

        disciplinas.addNotas(notasBack);
        assertThat(disciplinas.getNotas()).containsOnly(notasBack);
        assertThat(notasBack.getDisciplinas()).isEqualTo(disciplinas);

        disciplinas.removeNotas(notasBack);
        assertThat(disciplinas.getNotas()).doesNotContain(notasBack);
        assertThat(notasBack.getDisciplinas()).isNull();

        disciplinas.notas(new HashSet<>(Set.of(notasBack)));
        assertThat(disciplinas.getNotas()).containsOnly(notasBack);
        assertThat(notasBack.getDisciplinas()).isEqualTo(disciplinas);

        disciplinas.setNotas(new HashSet<>());
        assertThat(disciplinas.getNotas()).doesNotContain(notasBack);
        assertThat(notasBack.getDisciplinas()).isNull();
    }

    @Test
    void professoresTest() {
        Disciplinas disciplinas = getDisciplinasRandomSampleGenerator();
        Professores professoresBack = getProfessoresRandomSampleGenerator();

        disciplinas.setProfessores(professoresBack);
        assertThat(disciplinas.getProfessores()).isEqualTo(professoresBack);

        disciplinas.professores(null);
        assertThat(disciplinas.getProfessores()).isNull();
    }
}
