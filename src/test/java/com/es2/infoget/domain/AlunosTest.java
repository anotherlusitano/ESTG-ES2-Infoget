package com.es2.infoget.domain;

import static com.es2.infoget.domain.AlunosTestSamples.*;
import static com.es2.infoget.domain.CursosTestSamples.*;
import static com.es2.infoget.domain.NotasTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.es2.infoget.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AlunosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alunos.class);
        Alunos alunos1 = getAlunosSample1();
        Alunos alunos2 = new Alunos();
        assertThat(alunos1).isNotEqualTo(alunos2);

        alunos2.setId(alunos1.getId());
        assertThat(alunos1).isEqualTo(alunos2);

        alunos2 = getAlunosSample2();
        assertThat(alunos1).isNotEqualTo(alunos2);
    }

    @Test
    void notasTest() {
        Alunos alunos = getAlunosRandomSampleGenerator();
        Notas notasBack = getNotasRandomSampleGenerator();

        alunos.addNotas(notasBack);
        assertThat(alunos.getNotas()).containsOnly(notasBack);
        assertThat(notasBack.getAlunos()).isEqualTo(alunos);

        alunos.removeNotas(notasBack);
        assertThat(alunos.getNotas()).doesNotContain(notasBack);
        assertThat(notasBack.getAlunos()).isNull();

        alunos.notas(new HashSet<>(Set.of(notasBack)));
        assertThat(alunos.getNotas()).containsOnly(notasBack);
        assertThat(notasBack.getAlunos()).isEqualTo(alunos);

        alunos.setNotas(new HashSet<>());
        assertThat(alunos.getNotas()).doesNotContain(notasBack);
        assertThat(notasBack.getAlunos()).isNull();
    }

    @Test
    void cursosTest() {
        Alunos alunos = getAlunosRandomSampleGenerator();
        Cursos cursosBack = getCursosRandomSampleGenerator();

        alunos.setCursos(cursosBack);
        assertThat(alunos.getCursos()).isEqualTo(cursosBack);

        alunos.cursos(null);
        assertThat(alunos.getCursos()).isNull();
    }
}
