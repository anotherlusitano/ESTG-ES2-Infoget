import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Notas from './notas';
import NotasDetail from './notas-detail';
import NotasUpdate from './notas-update';
import NotasDeleteDialog from './notas-delete-dialog';

const NotasRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Notas />} />
    <Route path="new" element={<NotasUpdate />} />
    <Route path=":id">
      <Route index element={<NotasDetail />} />
      <Route path="edit" element={<NotasUpdate />} />
      <Route path="delete" element={<NotasDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NotasRoutes;
