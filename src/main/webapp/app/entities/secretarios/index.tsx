import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Secretarios from './secretarios';
import SecretariosDetail from './secretarios-detail';
import SecretariosUpdate from './secretarios-update';
import SecretariosDeleteDialog from './secretarios-delete-dialog';

const SecretariosRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Secretarios />} />
    <Route path="new" element={<SecretariosUpdate />} />
    <Route path=":id">
      <Route index element={<SecretariosDetail />} />
      <Route path="edit" element={<SecretariosUpdate />} />
      <Route path="delete" element={<SecretariosDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SecretariosRoutes;
