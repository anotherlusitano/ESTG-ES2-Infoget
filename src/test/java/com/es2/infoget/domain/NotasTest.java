package com.es2.infoget.domain;

import static com.es2.infoget.domain.AlunosTestSamples.*;
import static com.es2.infoget.domain.DisciplinasTestSamples.*;
import static com.es2.infoget.domain.NotasTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.es2.infoget.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notas.class);
        Notas notas1 = getNotasSample1();
        Notas notas2 = new Notas();
        assertThat(notas1).isNotEqualTo(notas2);

        notas2.setId(notas1.getId());
        assertThat(notas1).isEqualTo(notas2);

        notas2 = getNotasSample2();
        assertThat(notas1).isNotEqualTo(notas2);
    }

    @Test
    void alunosTest() {
        Notas notas = getNotasRandomSampleGenerator();
        Alunos alunosBack = getAlunosRandomSampleGenerator();

        notas.setAlunos(alunosBack);
        assertThat(notas.getAlunos()).isEqualTo(alunosBack);

        notas.alunos(null);
        assertThat(notas.getAlunos()).isNull();
    }

    @Test
    void disciplinasTest() {
        Notas notas = getNotasRandomSampleGenerator();
        Disciplinas disciplinasBack = getDisciplinasRandomSampleGenerator();

        notas.setDisciplinas(disciplinasBack);
        assertThat(notas.getDisciplinas()).isEqualTo(disciplinasBack);

        notas.disciplinas(null);
        assertThat(notas.getDisciplinas()).isNull();
    }
}
