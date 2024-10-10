import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Disciplinas from './disciplinas';
import DisciplinasDetail from './disciplinas-detail';
import DisciplinasUpdate from './disciplinas-update';
import DisciplinasDeleteDialog from './disciplinas-delete-dialog';

const DisciplinasRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Disciplinas />} />
    <Route path="new" element={<DisciplinasUpdate />} />
    <Route path=":id">
      <Route index element={<DisciplinasDetail />} />
      <Route path="edit" element={<DisciplinasUpdate />} />
      <Route path="delete" element={<DisciplinasDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DisciplinasRoutes;
