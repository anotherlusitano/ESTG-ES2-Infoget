import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CursoDisciplina from './curso-disciplina';
import CursoDisciplinaDetail from './curso-disciplina-detail';
import CursoDisciplinaUpdate from './curso-disciplina-update';
import CursoDisciplinaDeleteDialog from './curso-disciplina-delete-dialog';

const CursoDisciplinaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CursoDisciplina />} />
    <Route path="new" element={<CursoDisciplinaUpdate />} />
    <Route path=":id">
      <Route index element={<CursoDisciplinaDetail />} />
      <Route path="edit" element={<CursoDisciplinaUpdate />} />
      <Route path="delete" element={<CursoDisciplinaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CursoDisciplinaRoutes;
