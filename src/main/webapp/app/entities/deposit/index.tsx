import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Deposit from './deposit';
import DepositDetail from './deposit-detail';
import DepositUpdate from './deposit-update';
import DepositDeleteDialog from './deposit-delete-dialog';

const DepositRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Deposit />} />
    <Route path="new" element={<DepositUpdate />} />
    <Route path=":id">
      <Route index element={<DepositDetail />} />
      <Route path="edit" element={<DepositUpdate />} />
      <Route path="delete" element={<DepositDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DepositRoutes;
