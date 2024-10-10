import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Cursos from './cursos';
import CursosDetail from './cursos-detail';
import CursosUpdate from './cursos-update';
import CursosDeleteDialog from './cursos-delete-dialog';

const CursosRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Cursos />} />
    <Route path="new" element={<CursosUpdate />} />
    <Route path=":id">
      <Route index element={<CursosDetail />} />
      <Route path="edit" element={<CursosUpdate />} />
      <Route path="delete" element={<CursosDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CursosRoutes;
