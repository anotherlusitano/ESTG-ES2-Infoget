import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Alunos from './alunos';
import AlunosDetail from './alunos-detail';
import AlunosUpdate from './alunos-update';
import AlunosDeleteDialog from './alunos-delete-dialog';

const AlunosRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Alunos />} />
    <Route path="new" element={<AlunosUpdate />} />
    <Route path=":id">
      <Route index element={<AlunosDetail />} />
      <Route path="edit" element={<AlunosUpdate />} />
      <Route path="delete" element={<AlunosDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AlunosRoutes;
