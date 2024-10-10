import secretarios from 'app/entities/secretarios/secretarios.reducer';
import cursos from 'app/entities/cursos/cursos.reducer';
import cursoDisciplina from 'app/entities/curso-disciplina/curso-disciplina.reducer';
import disciplinas from 'app/entities/disciplinas/disciplinas.reducer';
import professores from 'app/entities/professores/professores.reducer';
import notas from 'app/entities/notas/notas.reducer';
import alunos from 'app/entities/alunos/alunos.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  secretarios,
  cursos,
  cursoDisciplina,
  disciplinas,
  professores,
  notas,
  alunos,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
