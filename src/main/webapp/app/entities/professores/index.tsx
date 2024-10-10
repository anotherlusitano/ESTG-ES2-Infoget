import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Professores from './professores';
import ProfessoresDetail from './professores-detail';
import ProfessoresUpdate from './professores-update';
import ProfessoresDeleteDialog from './professores-delete-dialog';

const ProfessoresRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Professores />} />
    <Route path="new" element={<ProfessoresUpdate />} />
    <Route path=":id">
      <Route index element={<ProfessoresDetail />} />
      <Route path="edit" element={<ProfessoresUpdate />} />
      <Route path="delete" element={<ProfessoresDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProfessoresRoutes;
