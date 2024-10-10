import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Secretarios from './secretarios';
import Cursos from './cursos';
import CursoDisciplina from './curso-disciplina';
import Disciplinas from './disciplinas';
import Professores from './professores';
import Notas from './notas';
import Alunos from './alunos';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="secretarios/*" element={<Secretarios />} />
        <Route path="cursos/*" element={<Cursos />} />
        <Route path="curso-disciplina/*" element={<CursoDisciplina />} />
        <Route path="disciplinas/*" element={<Disciplinas />} />
        <Route path="professores/*" element={<Professores />} />
        <Route path="notas/*" element={<Notas />} />
        <Route path="alunos/*" element={<Alunos />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
