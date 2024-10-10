package com.es2.infoget.domain;

import static com.es2.infoget.domain.CursoDisciplinaTestSamples.*;
import static com.es2.infoget.domain.CursosTestSamples.*;
import static com.es2.infoget.domain.DisciplinasTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.es2.infoget.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CursoDisciplinaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CursoDisciplina.class);
        CursoDisciplina cursoDisciplina1 = getCursoDisciplinaSample1();
        CursoDisciplina cursoDisciplina2 = new CursoDisciplina();
        assertThat(cursoDisciplina1).isNotEqualTo(cursoDisciplina2);

        cursoDisciplina2.setId(cursoDisciplina1.getId());
        assertThat(cursoDisciplina1).isEqualTo(cursoDisciplina2);

        cursoDisciplina2 = getCursoDisciplinaSample2();
        assertThat(cursoDisciplina1).isNotEqualTo(cursoDisciplina2);
    }

    @Test
    void cursosTest() {
        CursoDisciplina cursoDisciplina = getCursoDisciplinaRandomSampleGenerator();
        Cursos cursosBack = getCursosRandomSampleGenerator();

        cursoDisciplina.setCursos(cursosBack);
        assertThat(cursoDisciplina.getCursos()).isEqualTo(cursosBack);

        cursoDisciplina.cursos(null);
        assertThat(cursoDisciplina.getCursos()).isNull();
    }

    @Test
    void disciplinasTest() {
        CursoDisciplina cursoDisciplina = getCursoDisciplinaRandomSampleGenerator();
        Disciplinas disciplinasBack = getDisciplinasRandomSampleGenerator();

        cursoDisciplina.setDisciplinas(disciplinasBack);
        assertThat(cursoDisciplina.getDisciplinas()).isEqualTo(disciplinasBack);

        cursoDisciplina.disciplinas(null);
        assertThat(cursoDisciplina.getDisciplinas()).isNull();
    }
}
