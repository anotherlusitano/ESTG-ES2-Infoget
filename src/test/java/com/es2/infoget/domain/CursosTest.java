package com.es2.infoget.domain;

import static com.es2.infoget.domain.AlunosTestSamples.*;
import static com.es2.infoget.domain.CursoDisciplinaTestSamples.*;
import static com.es2.infoget.domain.CursosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.es2.infoget.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CursosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cursos.class);
        Cursos cursos1 = getCursosSample1();
        Cursos cursos2 = new Cursos();
        assertThat(cursos1).isNotEqualTo(cursos2);

        cursos2.setId(cursos1.getId());
        assertThat(cursos1).isEqualTo(cursos2);

        cursos2 = getCursosSample2();
        assertThat(cursos1).isNotEqualTo(cursos2);
    }

    @Test
    void alunosTest() {
        Cursos cursos = getCursosRandomSampleGenerator();
        Alunos alunosBack = getAlunosRandomSampleGenerator();

        cursos.addAlunos(alunosBack);
        assertThat(cursos.getAlunos()).containsOnly(alunosBack);
        assertThat(alunosBack.getCursos()).isEqualTo(cursos);

        cursos.removeAlunos(alunosBack);
        assertThat(cursos.getAlunos()).doesNotContain(alunosBack);
        assertThat(alunosBack.getCursos()).isNull();

        cursos.alunos(new HashSet<>(Set.of(alunosBack)));
        assertThat(cursos.getAlunos()).containsOnly(alunosBack);
        assertThat(alunosBack.getCursos()).isEqualTo(cursos);

        cursos.setAlunos(new HashSet<>());
        assertThat(cursos.getAlunos()).doesNotContain(alunosBack);
        assertThat(alunosBack.getCursos()).isNull();
    }

    @Test
    void cursoDisciplinaTest() {
        Cursos cursos = getCursosRandomSampleGenerator();
        CursoDisciplina cursoDisciplinaBack = getCursoDisciplinaRandomSampleGenerator();

        cursos.addCursoDisciplina(cursoDisciplinaBack);
        assertThat(cursos.getCursoDisciplinas()).containsOnly(cursoDisciplinaBack);
        assertThat(cursoDisciplinaBack.getCursos()).isEqualTo(cursos);

        cursos.removeCursoDisciplina(cursoDisciplinaBack);
        assertThat(cursos.getCursoDisciplinas()).doesNotContain(cursoDisciplinaBack);
        assertThat(cursoDisciplinaBack.getCursos()).isNull();

        cursos.cursoDisciplinas(new HashSet<>(Set.of(cursoDisciplinaBack)));
        assertThat(cursos.getCursoDisciplinas()).containsOnly(cursoDisciplinaBack);
        assertThat(cursoDisciplinaBack.getCursos()).isEqualTo(cursos);

        cursos.setCursoDisciplinas(new HashSet<>());
        assertThat(cursos.getCursoDisciplinas()).doesNotContain(cursoDisciplinaBack);
        assertThat(cursoDisciplinaBack.getCursos()).isNull();
    }
}
